package com.marcelmika.lims.portal.service;

import com.marcelmika.lims.events.session.BuddyLoginRequestEvent;
import com.marcelmika.lims.events.session.BuddyLoginResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 7:00 PM
 */
public interface BuddyPortalService {

    /**
     * Login buddy to Portal
     * @param event Request event for login method
     * @return Response event for login method
     */
    public BuddyLoginResponseEvent loginBuddy(BuddyLoginRequestEvent event);

}
