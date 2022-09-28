# 金蝶ERP对接SDK（msk3cloud-webapi）

1、前期准备

> 当你需要使用该项目时，你需要先本地 install 金蝶的sdk  
>
> 在 resources/lib 中有 `sdk_install.bat` 脚本

2、下载当前项目到本地

> install 到本地，这样你就可以在你的另一个应用项目中依赖它，并使用它。

3、pom 依赖

> 一个是 封装的SDK ，一个是金蝶的SDK

```xml

<dependency>
    <groupId>com.msk3cloud</groupId>
    <artifactId>msk3cloud-webapi</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>

<dependency>
    <groupId>com.k3cloud</groupId>
    <artifactId>k3cloud-webapi-sdk</artifactId>
    <version>7.9</version>
</dependency>
```

4、简单查询

```java
/**
 * desc:
 *
 * @author Rao
 * @Date 2022/06/07
 **/
public class JinDieMain {

    public static void main(String[] args) throws Exception {
        K3CloudApiConfig k3CloudApiConfig = new K3CloudApiConfig();
        k3CloudApiConfig.setAcctId("xxx");
        k3CloudApiConfig.setAppId("xxx+xx/xxx");
        k3CloudApiConfig.setAppSecret("xxxx");
        k3CloudApiConfig.setUserName("xxx");
        k3CloudApiConfig.setPassword("xxxx!");
        k3CloudApiConfig.setServerUrl("htxxxxxxxxud/");
       // 若不需要则不设置
        k3CloudApiConfig.setProxy("http:xxxxxx28");

        K3CloudApiClient k3CloudApiClient = new K3CloudApiClient(k3CloudApiConfig);
        K3cloudRemoteService k3cloudRemoteService = new K3cloudRemoteServiceImpl(k3CloudApiClient);


        QueryCondition queryCondition = QueryCondition.build( SupplierChainConstants.PUR_PURCHASEORDER)
                .select("FID")
                .eq("FBillNo","xxxx")
                .page(1, 100000);

        List<JSONObject> jsonObjects = k3cloudRemoteService.documentQuery(queryCondition.queryParam());
        System.out.println(jsonObjects);
    }
}
```

## 查询（QueryCondition）

```text
1、QueryParam 使用
 参考 PurchaseOrderVo.class  字段需要使用 @JSONField (因为我使用的是 fastjson来做解析)
 查询  FEntryID 需要 使用 对象_FEntryID  ,eg:  FPOOrderEntry_FEntryID
 查询的字段有 属于Number类下的 则 需要使用 字段.fnumber  fnumber 不关心大小写
```
## 保存（SaveCondition）

```text
2、SaveParam 使用
保存或更新对象的字段需要是要符合要求,要么加注解,要么字段要一样
应该 fastjson的 JSONField or gson 的 @SerializedName("") 
由于 金蝶使用的是 gson ,而我封装的json解析使用的是 fastjson,ememem(算了不移除了,就这样用吧)
也就是 保存 使用的 字段注解应该是 @SerializedName  
```