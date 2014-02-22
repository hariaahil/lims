
package com.marcelmika.lims.portal.poller;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.poller.PollerResponse;
import com.marcelmika.lims.events.ResponseEvent;

/**
 * Poller dispatcher is responsible for calling the appropriate method
 * on poller processor based on the chunkId which is taken from the poller request.
 * ChunkId servers as an identifier of the action which is requested.
 * For example if the user attempts to create a new message the poller request with
 * poller.action.create.message is sent to the poller processor. Based on that the poller
 * dispatcher calls createMessage() method on the poller processor.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class PollerDispatcher {

    // Actions
    public static final String POLLER_ACTION_CREATE_MESSAGE = "poller.action.create.message";
    public static final String POLLER_ACTION_OPEN_CONVERSATION = "poller.action.open.conversation";
    public static final String POLLER_ACTION_CLOSE_CONVERSATION = "poller.action.close.conversation";
    public static final String POLLER_ACTION_LEAVE_CONVERSATION = "poller.action.leave.conversation";
    public static final String POLLER_ACTION_ADD_TO_CONVERSATION = "poller.action.add.to.conversation";
    public static final String POLLER_ACTION_SEND_MESSAGE = "poller.action.send.message";
    public static final String POLLER_ACTION_SAVE_SETTINGS = "poller.action.save.settings";
    public static final String POLLER_ACTION_CHANGE_STATUS = "poller.action.change.status";
    public static final String POLLER_ACTION_CHANGE_ACTIVE_PANEL = "poller.action.change.active.panel";
    public static final String POLLER_ACTION_CHANGE_ACTIVE_ROOM_TYPE = "poller.action.change.active.room.type";
    public static final String POLLER_ACTION_CHAT_ENABLED = "poller.action.chat.enabled";
    // Log
    private static Log log = LogFactoryUtil.getLog(ChatPollerProcessor.class);

    /**
     * Calls appropriate method on ChatPollerProcessor based on the chunkId taken from the poller request.
     *
     * @param pollerRequest   request from browser
     * @param pollerProcessor poller processor
     * @return ResponseEvent return by the method
     * @throws IllegalArgumentException if request does not contain chunkId
     */
    public static ResponseEvent dispatchSendRequest(PollerRequest pollerRequest,
                                                    ChatPollerProcessor pollerProcessor)
            throws IllegalArgumentException {

        // Get the chunk id from the request.
        String chunkId = pollerRequest.getChunkId();
        if (chunkId == null) {
            throw new IllegalArgumentException("Chunk id cannot be null");
        }

        // Response event as s result of a request
        ResponseEvent responseEvent = null;

        // Create conversation
        if (chunkId.equals(PollerDispatcher.POLLER_ACTION_CREATE_MESSAGE)) {
            responseEvent = pollerProcessor.createConversation(pollerRequest);
        } // Open conversation
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_OPEN_CONVERSATION)) {
            responseEvent = pollerProcessor.openConversation(pollerRequest);
        } // Close conversation
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_CLOSE_CONVERSATION)) {
            responseEvent = pollerProcessor.closeConversation(pollerRequest);
        } // Leave conversation
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_LEAVE_CONVERSATION)) {
            responseEvent = pollerProcessor.leaveConversation(pollerRequest);
        } // Add to conversation
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_ADD_TO_CONVERSATION)) {
            // @todo: Not implemented in v0.2
            // addToConversation(pollerRequest);
        } // Send Message
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_SEND_MESSAGE)) {
            responseEvent = pollerProcessor.sendMessage(pollerRequest);
        } // Save settings
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_SAVE_SETTINGS)) {
            responseEvent = pollerProcessor.updateSettings(pollerRequest);
        } // Change status
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_CHANGE_STATUS)) {
            responseEvent = pollerProcessor.updateStatus(pollerRequest);
        } // Change active panel
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_CHANGE_ACTIVE_PANEL)) {
            responseEvent = pollerProcessor.changeActivePanel(pollerRequest);
        } // Enable/Disable chat
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_CHAT_ENABLED)) {
            responseEvent = pollerProcessor.setChatEnabled(pollerRequest);
        } // Change active room type
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_CHANGE_ACTIVE_ROOM_TYPE)) {
            responseEvent = pollerProcessor.updateActiveRoomType(pollerRequest);
        }

        // Log
        log.info("Dispatcher: Send: " + chunkId);

        return responseEvent;
    }


    /**
     * Calls all appropriate method on ChatPollerProcessor that are scheduled for the receive request event.
     *
     * @param pollerRequest request from browser
     * @param pollerResponse response sent to browser
     * @param pollerProcessor poller processor
     */
    public static void dispatchReceiveRequest(PollerRequest pollerRequest,
                                              PollerResponse pollerResponse,
                                              ChatPollerProcessor pollerProcessor) {

        // Run methods on poller processor
        pollerProcessor.getBuddyList(pollerRequest, pollerResponse);
        pollerProcessor.getAllConversations(pollerRequest, pollerResponse);
        pollerProcessor.getOpenedConversations(pollerRequest, pollerResponse);

        // Log
        log.info("Dispatcher: Receive");
    }

}
