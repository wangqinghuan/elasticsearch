/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.ml.rest.calendar;

import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.action.AcknowledgedRestListener;
import org.elasticsearch.xpack.ml.MachineLearning;
import org.elasticsearch.xpack.ml.action.DeleteCalendarEventAction;
import org.elasticsearch.xpack.ml.calendars.Calendar;
import org.elasticsearch.xpack.ml.calendars.ScheduledEvent;

import java.io.IOException;

public class RestDeleteCalendarEventAction extends BaseRestHandler {

    public RestDeleteCalendarEventAction(Settings settings, RestController controller) {
        super(settings);
        controller.registerHandler(RestRequest.Method.DELETE,
                MachineLearning.BASE_PATH + "calendars/{" + Calendar.ID.getPreferredName() + "}/events/{" +
                        ScheduledEvent.EVENT_ID.getPreferredName() + "}", this);
    }

    @Override
    public String getName() {
        return "xpack_ml_delete_calendar_event_action";
    }

    @Override
    protected BaseRestHandler.RestChannelConsumer prepareRequest(RestRequest restRequest, NodeClient client) throws IOException {
        String eventId = restRequest.param(ScheduledEvent.EVENT_ID.getPreferredName());
        String calendarId = restRequest.param(Calendar.ID.getPreferredName());
        DeleteCalendarEventAction.Request request = new DeleteCalendarEventAction.Request(calendarId, eventId);
        return channel -> client.execute(DeleteCalendarEventAction.INSTANCE, request, new AcknowledgedRestListener<>(channel));
    }
}
