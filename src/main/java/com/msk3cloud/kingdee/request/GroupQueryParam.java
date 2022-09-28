package com.msk3cloud.kingdee.request;

import com.kingdee.bos.webapi.sdk.JsonBase;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
public class GroupQueryParam extends JsonBase {
    private String GroupFieldKey;
    private String GroupPkIds;
    private String Ids;
    private String FormId;
}
