package com.msk3cloud.kingdee.entity.login;

import lombok.Data;
import com.google.gson.annotations.SerializedName;

/**
 * @author Rao
 * @Date 2022/01/06
 **/
@Data
public class Charset {
    @SerializedName("BodyName")
    private String bodyname;
    @SerializedName("EncodingName")
    private String encodingname;
    @SerializedName("HeaderName")
    private String headername;
    @SerializedName("WebName")
    private String webname;
    @SerializedName("WindowsCodePage")
    private Integer windowscodepage;
    @SerializedName("IsBrowserDisplay")
    private Boolean isbrowserdisplay;
    @SerializedName("IsBrowserSave")
    private Boolean isbrowsersave;
    @SerializedName("IsMailNewsDisplay")
    private Boolean ismailnewsdisplay;
    @SerializedName("IsMailNewsSave")
    private Boolean ismailnewssave;
    @SerializedName("IsSingleByte")
    private Boolean issinglebyte;
    @SerializedName("EncoderFallback")
    private Encoderfallback encoderfallback;
    @SerializedName("DecoderFallback")
    private Decoderfallback decoderfallback;
    @SerializedName("IsReadOnly")
    private Boolean isreadonly;
    @SerializedName("CodePage")
    private Integer codepage;
}
