package cn.ipanel.questionnaireserver;


import cn.ipanel.questionnaireserver.vo.QuestionBody;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class QuestionTest {

    /**
     * 测试question_body字段解析
     */
    @Test
    public void testQuestionBody(){

        String JsonStr ="[\n" +
                "  {\n" +
                "    \"code\": \"A\",\n" +
                "    \"content\": \"上海\",\n" +
                "    \"desc\": \"XXXXXX\",\n" +
                "    \"pic\": \"http://192.168.2.3/XX\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"code\": \"A\",\n" +
                "    \"content\": \"上海\",\n" +
                "    \"desc\": \"XXXXXX\",\n" +
                "    \"pic\": \"http://192.168.2.3/XX\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"code\": \"A\",\n" +
                "    \"content\": \"上海\",\n" +
                "    \"desc\": \"XXXXXX\",\n" +
                "    \"pic\": \"http://192.168.2.3/XX\"\n" +
                "  }\n" +
                "]";

        List<QuestionBody> questionBodies = JSON.parseArray(JsonStr, QuestionBody.class);
        questionBodies.forEach(q->System.out.println(q.toString()));

    }

    @Test
    public void test(){
        HashMap<Long, String> map = new HashMap<>();
        map.put(1L,"A");
        map.put(2L,"B");
        map.put(3L,"C");
        map.put(4L,"D");
        System.out.println("map.get(5L) = " + map.get(5L));
    }

}
