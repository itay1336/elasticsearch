/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.search.aggregations.bucket.histogram;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.aggregations.ValuesSourceAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilderException;

import java.io.IOException;

/**
 * A builder for a histogram aggregation.
 */
public class HistogramBuilder extends ValuesSourceAggregationBuilder<HistogramBuilder> {

    private Long interval;
    private Histogram.Order order;
    private Long minDocCount;

    /**
     * Constructs a new histogram aggregation builder.
     *
     * @param name  The name of the aggregation (will serve as the unique identifier for the aggregation result in the response)
     */
    public HistogramBuilder(String name) {
        super(name, InternalHistogram.TYPE.name());
    }

    /**
     * Sets the interval for the histogram.
     *
     * @param interval  The interval for the histogram
     * @return          This builder
     */
    public HistogramBuilder interval(long interval) {
        this.interval = interval;
        return this;
    }

    /**
     * Sets the order by which the buckets will be returned.
     *
     * @param order The order by which the buckets will be returned
     * @return      This builder
     */
    public HistogramBuilder order(Histogram.Order order) {
        this.order = order;
        return this;
    }

    /**
     * Sets the minimum document count per bucket. Buckets with less documents than this min value will not be returned.
     *
     * @param minDocCount   The minimum document count per bucket
     * @return              This builder
     */
    public HistogramBuilder minDocCount(long minDocCount) {
        this.minDocCount = minDocCount;
        return this;
    }

    @Override
    protected XContentBuilder doInternalXContent(XContentBuilder builder, Params params) throws IOException {
        if (interval == null) {
            throw new SearchSourceBuilderException("[interval] must be defined for histogram aggregation [" + name + "]");
        }
        builder.field("interval", interval);

        if (order != null) {
            builder.field("order");
            order.toXContent(builder, params);
        }

        if (minDocCount != null) {
            builder.field("min_doc_count", minDocCount);
        }

        return builder;
    }

}
