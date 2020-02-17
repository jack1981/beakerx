/*
 *  Copyright 2017 TWO SIGMA OPEN SOURCE, LLC
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.twosigma.beakerx.jvm.serialization;

import com.fasterxml.jackson.databind.JsonNode;
import com.twosigma.beakerx.KernelTest;
import com.twosigma.beakerx.jvm.object.GridOutputContainerLayoutManager;
import com.twosigma.beakerx.kernel.KernelManager;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class GridOutputContainerLayoutManagerSerializerTest {
  private GridOutputContainerLayoutManager layoutManager;
  private static GridOutputContainerLayoutManagerSerializer serializer;
  private static SerializationTestHelper<GridOutputContainerLayoutManagerSerializer,
          GridOutputContainerLayoutManager> helper;

  @BeforeClass
  public static void setUpClass() throws IOException {
    serializer = new GridOutputContainerLayoutManagerSerializer(new BasicObjectSerializer());
    helper = new SerializationTestHelper<>(serializer);
  }

  @Before
  public void setUp() throws Exception {
    KernelManager.register(new KernelTest());
    layoutManager = new GridOutputContainerLayoutManager(5) {
    };
  }

  @After
  public void tearDown() throws Exception {
    KernelManager.register(null);
  }

  @Test
  public void serializeGridOutputContainerLayoutManager_resultJsonHasType() throws IOException {
    //when
    JsonNode actualObj = helper.serializeObject(layoutManager);
    //then
    Assertions.assertThat(actualObj.has("type")).isTrue();
    Assertions.assertThat(actualObj.get("type").asText()).isEqualTo("GridOutputContainerLayoutManager");
  }

  @Test
  public void serializeColumns_resultJsonHasColumns() throws IOException {
    int num = layoutManager.getColumns();
    //when
    JsonNode actualObj = helper.serializeObject(layoutManager);
    //then
    Assertions.assertThat(actualObj.has("columns")).isTrue();
    Assertions.assertThat(actualObj.get("columns").asInt()).isEqualTo(num);
  }

}
