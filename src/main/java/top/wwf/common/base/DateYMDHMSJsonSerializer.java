package top.wwf.common.base;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import top.wwf.common.consts.Const;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateYMDHMSJsonSerializer extends JsonSerializer<Date> {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DATE_FORMAT);
    public static SimpleDateFormat dateFormatToNum=new SimpleDateFormat(Const.DATE_FORMAT_TO_NUM);
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        try {
            jsonGenerator.writeString(dateFormat.format(date));
        } catch (Exception e) {
            jsonGenerator.writeString(String.valueOf(date.getTime()));
        }
    }

}
