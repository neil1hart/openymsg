package org.openymsg.conference;

import java.util.Set;

import org.openymsg.Conference;
import org.openymsg.ConferenceStatus;
import org.openymsg.Contact;

/**
 * Conference Services for Yahoo. 
 * Not sure if the conferenceId can contain non-alphanumeric characters.
 * Also unsure of Unicode characters for any of the String field.
 * @author neilhart
 *
 */
public interface SessionConference {
	/**
	 * Create a conference and invite a set of yahoo ids.  The Contacts do not need to be in the your list of contacts.
	 * Either the conferenceId or the message will show as the conference name for the invited users.  
	 * @param conferenceId unique name for the conference.  Sometimes this shows as the conference name.
	 * @param contacts set of yahoo ids to invite  
	 * @param message invite message.  Sometimes this shows as the conference name.  Can be null.
	 * @return new Conference
	 */
	Conference createConference(String conferenceId, Set<Contact> contacts, String message) ;

	/**
	 * Send a message to the conference.
	 * @param conference conference to send the message
	 * @param message message to send to the conference
	 * @throws IllegalArgumentException if conference doesn't exist
	 */
	void sendConferenceMessage(Conference conference, String message) throws IllegalArgumentException;

	/**
	 * Leave a conference.  One of the members may re-invite you to the conference. 
	 * @param conference conference leave
	 * @throws IllegalArgumentException if conference doesn't exist
	 */
	void leaveConference(Conference conference) throws IllegalArgumentException;

	/**
	 * Accept a conference invite that was sent to you.
	 * @param conference conference in the invite
	 * @throws IllegalArgumentException if conference doesn't exist
	 */
	void acceptConferenceInvite(Conference conference) throws IllegalArgumentException;

	/**
	 * Decline a conference invite that was sent to you with a message
	 * @param conference conference in the invite
	 * @param message decline reason sent to the inviter, not the conference.  May be null
	 * @throws IllegalArgumentException if conference doesn't exist
	 */
	void declineConferenceInvite(Conference conference, String message) throws IllegalArgumentException;

	/**
	 * Invite another yahoo id to the conference with a messages.  contact does not need to be current contact.
	 * @param conference conference in the invite
	 * @param contact yahoo id to invite
	 * @param message invite message.  May be null
	 * @throws IllegalArgumentException if conference doesn't exist
	 */
	void extendConference(Conference conference, Contact contact, String message) throws IllegalArgumentException;
	
	/**
	 * Get the Conference with the matching id.  Null if id is not matched.
	 * @param conferenceId conference id to find
	 * @return matching Conference, null if not found.
	 */
	Conference getConference(String conferenceId);
	
	/**
	 * get the ConferenceStatus with the matching id.  Null if id is not matched.
	 * @param conferenceId conference id to find
	 * @return matching ConferenceStatus, null if not found.
	 */
	ConferenceStatus getConferenceStatus(String conferenceId);
}
