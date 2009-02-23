package org.openymsg.v1.roster;

import org.openymsg.network.FriendManager;
import org.openymsg.roster.RosterSyncedAddMethodTriggersUpdate;
import org.openymsg.v1.network.YahooUserV1;

public class RosterV1SyncedAddMethodTriggersUpdate extends RosterSyncedAddMethodTriggersUpdate<RosterV1, YahooUserV1> {

	@Override
	protected YahooUserV1 createUser(String userId) {
		return new YahooUserV1(userId);
	}

	@Override
	protected RosterV1 createRoster(final FriendManager manager) {
		return new RosterV1(manager);
	}
	
	@Override
	protected boolean addUserToRoster(RosterV1 roster, YahooUserV1 user) throws Throwable {
		return roster.syncedAdd(user);
	}
}
