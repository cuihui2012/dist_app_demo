package com.cuihui.provider.providerdemo.utils;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.join.query.HasParentQueryBuilder;
import org.elasticsearch.join.query.JoinQueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


/**
 * es 工具类
 */
public class EsUtils {

    private static Logger logger = LoggerFactory.getLogger(EsUtils.class);

    //数据治理ES服务器
    private static String HOST_IP = "202.201.13.15";
    private static int TCP_PORT = 9300;
    private volatile static TransportClient client = getSingleTransportClient();

    /**
     * Elasticsearch Java API 的相关操作都是通过TransportClient对象与Elasticsearch集群进行交互的。
     * 为了避免每次请求都创建一个新的TransportClient对象,可以封装一个双重加锁单例模式返回TransportClient对象。
     * 即同时使用volatile和synchronized。volatile是Java提供的一种轻量级的同步机制,synchronized通常称为重量级同步锁。
     * @author moonxy
     */
    public static TransportClient getSingleTransportClient() {
        try {
            if(client == null) {
                synchronized(TransportClient.class) {
                    client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(
                            new InetSocketTransportAddress(InetAddress.getByName(HOST_IP), TCP_PORT));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }



    /**
     * 没有索引即创建索引，并插入一条map数据
     * 指定字段名称,字段类型都是text
     * @param index
     * @param type
     * @param id 数据ID,数据唯一标志
     * @param fields
     * @throws IOException
     */
    public static void indexCreateByColumn(String index, String type, String id, Map<String,String> fields) throws IOException {
        IndexResponse response = client.prepareIndex(index, type, id)
                .setSource(
                        jsonBuilder()
                                .startObject()
                                .field("id",fields.get("id"))
                                .field("access_time",fields.get("access_time"))
                                .field("user_code",fields.get("user_code"))
                                .field("sys_code",fields.get("sys_code"))
                                .field("client_ip",fields.get("client_ip"))
                                .field("url",fields.get("url"))
                                .field("app_id",fields.get("app_id"))
                                .endObject()
                )
                .get();
        System.out.println(response);
    }

    /**
     * 没有索引即创建索引，一次插入多条数据
     * 字段名称为map.key值,字段类型都是text
     * @param list
     */
    public static void indexCreateDefault(String index, String type, List<Map<String,Object>> list){
        if (list == null || list.size() ==0){
            return;
        }
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for(Map<String,Object> rec : list) {
            //数据_id使用uuid
            String id = UUID.randomUUID().toString().replace("-","");
            logger.debug("ES 插入数据===" + rec);
            IndexRequestBuilder builder = client.prepareIndex(index, type, id).setSource(rec);
            bulkRequest.add(builder);
        }
        bulkRequest.get();
    }

    /**
     * 创建一个指定各列的索引,指定了各字段的类型,使用到了join类型,同一类型下父子文档
     * @param indexname
     * @param typename
     * @throws IOException
     */
    public static void indexCreateDetail(String indexname, String typename) throws IOException {
            //1.预创建index
            client.admin().indices().prepareCreate(indexname).get();
    	    //2.创建mapping
			XContentBuilder builder=XContentFactory.jsonBuilder()
					.startObject()
					.startObject("properties")
					   .startObject("class").field("type", "keyword").field("index", true).endObject()
					   .startObject("classname").field("type", "keyword").field("index", true).endObject()
					   .startObject("flag").field("type", "keyword").field("index", false).endObject()
					   .startObject("uid").field("type", "keyword").field("index", true).endObject()
					   .startObject("uname").field("type", "keyword").endObject()
					   .startObject("sex").field("type", "keyword").field("index", false).endObject()
                       .startObject("location").field("type", "text").endObject()
					   .startObject("birthday").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
					   .startObject("join_field").field("type", "join")
                            .startObject("relations").field("father", "son").endObject().endObject()
					  .endObject()
					  .endObject();
			
			//3.创建索引,设置mapping
			PutMappingRequest request = new PutMappingRequest(indexname).type(typename);
			request.source(builder);
 
			PutMappingResponse mappingResp = client.admin().indices().putMapping(request).actionGet();
			client.close();
			System.out.println("mapping:"+mappingResp.toString());	
	}

    /**
     * 数据按父子文档插入
     * @param list
     * @param index
     * @param type
     */
    public static void insertData(List<Map<String,String>> list,String index,String type) {
        if (list == null || list.size() ==0){
            return;
        }
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for(Map<String,String> rec : list) {
            logger.debug("插入数据===" + rec);
            //是父级数据还是子级数据
            String flag = rec.get("flag");
            IndexRequestBuilder builder = client.prepareIndex(index, type, UUID.randomUUID().toString()).setSource(rec);
            if("son".equals(flag)){
                //父级文档_id
                builder.setRouting("44f6c989-510c-46d0-b2dd-030e2792c8ed");
            }
            bulkRequest.add(builder);
        }
        bulkRequest.get();
    }

    /**
     * 过滤分组检索数据<<<代码基于数据治理,未做测试>>>
     * @param viewTaskId
     * @param index
     * @param types
     * @return
     */
    public static Iterator<Bucket> searchIndexByGroup(String viewTaskId,String index ,String... types) {
        //screen_id-分组字段
        TermsAggregationBuilder screenAgg= AggregationBuilders.terms("resultCount").field("screen_id");
        //过滤字段
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("view_task_id",viewTaskId);
        TermQueryBuilder mainStepQueryBuilder = QueryBuilders.termQuery("main_step","1");
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.filter(matchQueryBuilder).filter(mainStepQueryBuilder);
        //父子文档,“base”没搞明白
        HasParentQueryBuilder hpqb = JoinQueryBuilders.hasParentQuery("base", queryBuilder, false);

        SearchResponse response = client.prepareSearch(index).setTypes(types)
                .addAggregation(screenAgg)
                .setQuery(hpqb)
                .get();

        Map<String, Aggregation> aggMap = response.getAggregations().asMap();

        org.elasticsearch.search.aggregations.bucket.terms.StringTerms screenAggTerm= (StringTerms) aggMap.get("resultCount");
        Iterator<Bucket> screenBucketIt = screenAggTerm.getBuckets().iterator();

        return screenBucketIt;
    }
    
    /**
     * 删除索引下的全部数据
     * @param index 索引
     */
    public static void clearAllDatas(String index) {
        BulkByScrollResponse response =
                new DeleteByQueryRequestBuilder(client, DeleteByQueryAction.INSTANCE)
                        .filter(QueryBuilders.rangeQuery("_id"))
                        .source(index)
                        .get();
        long deleted = response.getDeleted();
        System.out.println("删除索引下的所有数据,数据条数：" + deleted);
    }

    /**
     * 删除索引
     * @param index
     */
    public static void deleteIndex(String index){
        DeleteIndexResponse deleteIndexResponse = client.admin().indices()
                .prepareDelete(index)
                .execute().actionGet();
    }

    public static void main(String[] args) {
        List list = new ArrayList();
//        Map map = new HashMap();
//        map.put("id","id33");
//        map.put("access_time","access_time");
//        map.put("user_code","user_code");
//        map.put("sys_code","sys_code");
//        map.put("client_ip","client_ip");
//        map.put("url","url");
//        map.put("app_id","app_id");
//        list.add(map);
//        EsUtils.indexCreateDefault(list);
        //删除索引
//        EsUtils.deleteIndex("test_test");
//        EsUtils.deleteIndex("test_test_join");
//        try {
//            EsUtils.indexCreateDetail("test_test_join","test_test_join");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("class","class1");
//        map.put("classname","一班");

        map.put("flag","son");
        map.put("uid","1111");
        map.put("uname","1111");
        map.put("sex","1111");
        map.put("location","1111");
        map.put("birthday","1991-12-15");
        list.add(map);
        EsUtils.insertData(list,"test_test_join","test_test_join");
    }
}