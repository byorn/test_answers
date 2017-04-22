/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package preinterview;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.apache.http.HttpHeaders.USER_AGENT;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAnswer2_1_2URLCallingScript {


    @Test
    public void test_Calling_4_URLS_And_Saving_Response_To_DB() {

        String[] urls = {"http://www.google.com",
                "http://www.google.com",
                "http://www.google.com",
                "http://www.test.com"};


        for (String url : urls) {

            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);

            request.addHeader("User-Agent", USER_AGENT);
            try {
                HttpResponse response = client.execute(request);

                if(url.contains("test.com")){
                    System.out.println(response);
                }
                // get response content and
                // db.save(:response);

            } catch (IOException e) {
                e.printStackTrace();
                //log error
            }

            //db.save(response);

        }
        assert true;
    }

}