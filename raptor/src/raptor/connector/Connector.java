package raptor.connector;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.preference.PreferencePage;

import raptor.chat.ChatType;
import raptor.game.Game;
import raptor.game.Move;
import raptor.script.GameScript;
import raptor.service.ChatService;
import raptor.service.GameService;

public interface Connector {
	/**
	 * Adds a connector listener to the connector.
	 */
	public void addConnectorListener(ConnectorListener listener);

	/**
	 * TO DO: iron out what this is.
	 */
	public void addGameScript(GameScript script);

	/**
	 * Connects to the connector. The connection information should be stored as
	 * preferences.
	 */
	public void connect();

	/**
	 * Disconnects the connector.
	 */
	public void disconnect();

	/**
	 * Disposes the connector. This method should release any resources the
	 * connector is maintaining.
	 */
	public void dispose();

	/**
	 * Returns descriptions and messages to send to the connector. This is
	 * intended to be used to generate pop-up menus. Returns a String[n][2]
	 * where 0 is the description and 1 is the message to send to the connector.
	 */
	public String[][] getChannelActions(String channel);

	/**
	 * Returns the prefix to use when the user sends channel tells. On fics this
	 * is 'tell channelNumber '. e.g. ('tell 1 ')
	 */
	public String getChannelTabPrefix(String channel);

	/**
	 * Returns the chat service the connector maintains. All ChatEvents are
	 * published through this service.
	 */
	public ChatService getChatService();

	/**
	 * Returns the connectors long description
	 */
	public String getDescription();

	/**
	 * Returns descriptions and messages to send to the connector. This is
	 * intended to be used to generate pop-up menus. Returns a String[n][2]
	 * where 0 is the description and 1 is the message to send to the connector.
	 */
	public String[][] getGameIdActions(String gameId);

	/**
	 * TO DO: iron out what this is.
	 */
	public GameScript getGameScript(String name);

	/**
	 * TO DO: iron out what this is.
	 */
	public GameScript[] getGameScripts();

	/**
	 * Returns the game service the connector manages. All game events flow
	 * through this service.
	 */
	public GameService getGameService();

	/**
	 * Returns the menu manager to use in the RaptorWindow menu bar for this
	 * connector.
	 */
	public MenuManager getMenuManager();

	/**
	 * Returns the prefix to use for partner tells. On fics this would be 'ptell
	 * '
	 */
	public String getPartnerTellPrefix();

	/**
	 * Returns descriptions and messages to send to the connector. This is
	 * intended to be used to generate pop-up menus. Returns a String[n][2]
	 * where 0 is the description and 1 is the message to send to the connector.
	 */
	public String[][] getPersonActions(String person);

	/**
	 * Returns the prefix to use for person tells. On fics this would be 'tell
	 * person '
	 */
	public String getPersonTabPrefix(String person);

	/**
	 * Returns the prompt used by the connector. The result should not include
	 * any end of line terminators. A Fics connector should return 'fics%'
	 */
	public String getPrompt();

	/**
	 * Return the preference node to add to the root preference dialog. This
	 * preference node will show up with the connectors first name. You can add
	 * secondary nodes by implementing getSecondaryPreferenceNodes. These nodes
	 * will show up below the root node.
	 */
	public PreferencePage getRootPreferencePage();

	/**
	 * Returns an array of the secondary preference nodes.
	 */
	public PreferenceNode[] getSecondaryPreferenceNodes();

	/**
	 * Returns a short name describing this connector.
	 * 
	 * @return
	 */
	public String getShortName();

	/**
	 * Given a handle it returns it returns whta to prefix a tell to the handle
	 * with. For fics this would return 'tell handle '
	 */
	public String getTellToString(String handle);

	/**
	 * Returns the name of the current user logged in.
	 */
	public String getUserName();

	/**
	 * Returns true if the connector is connected.
	 */
	public boolean isConnected();

	/**
	 * Returns true if the connector is in the process of connecting.
	 */
	public boolean isConnecting();

	/**
	 * Returns true if the specified word is likely a channel. A call to
	 * parseChannel with this word would return the actual channel name.
	 */
	public boolean isLikelyChannel(String channel);

	/**
	 * Returns true if the specified word is likely a gameId. A call to
	 * parseGameId with this word would return the actual gameId.
	 */
	public boolean isLikelyGameId(String gameId);

	/**
	 * Returns true if the specified message is likely a partner tell message.
	 */
	public boolean isLikelyPartnerTell(String outboundMessage);

	/**
	 * Returns true if the specified word is likely a persons name. A call to
	 * parsePerson with this word would return the actual persons name.
	 */
	public boolean isLikelyPerson(String word);

	/**
	 * Makes the move in the specified game.
	 */
	public void makeMove(Game game, Move move);

	/**
	 * Handles sending an abort.
	 */
	public void onAbortKeyPress();

	/**
	 * Handles accepting a match request.
	 */
	public void onAcceptKeyPress();

	/**
	 * If this connector is setup to auto-connect it should auto connect when
	 * this method is invoked.
	 */
	public void onAutoConnect();

	/**
	 * Handles declining a match request.
	 */
	public void onDeclineKeyPress();

	/**
	 * Handles sending a draw request for the specified game.
	 */
	public void onDraw(Game game);

	/**
	 * Invoked when an error occurs. A good connector will send a nice message
	 * to the user through the ChatService.
	 */
	public void onError(String message);

	/**
	 * Invoked when an error occurs. A good connector will send a nice message
	 * to the user through the ChatService.
	 */
	public void onError(String message, Throwable t);

	/**
	 * Invoked when a user wants to go back in an examined game.
	 */
	public void onExamineModeBack(Game game);

	/**
	 * Invoked when a user wants to commit a line in an examined game.
	 */
	public void onExamineModeCommit(Game game);

	/**
	 * Invoked when a user wants to go to the first move in an examined game.
	 */
	public void onExamineModeFirst(Game game);

	/**
	 * Invoked when a user wants to go forward in an examined game.
	 */
	public void onExamineModeForward(Game game);

	/**
	 * Invoked when a user wants to go to the last move in an examined game.
	 */
	public void onExamineModeLast(Game game);

	/**
	 * Invoked when a user wants to revert back to the main line in an examined
	 * game.
	 */
	public void onExamineModeRevert(Game game);

	/**
	 * Sends a rematch request. On Fics this would be the rematch command.
	 */
	public void onRematchKeyPress();

	/**
	 * Resigns the specified game.
	 */
	public void onResign(Game game);

	/**
	 * Handles a request to clear a position being setup.
	 */
	public void onSetupClear(Game game);

	/**
	 * Handles a request to clear a square in a position being setup.
	 */
	public void onSetupClearSquare(Game game, int square);

	/**
	 * Handles a request to complete setup mode. On fics the user will enter
	 * examine mode.
	 */
	public void onSetupComplete(Game game);

	/**
	 * Handles a request to setup a position with the specified FEN notation.
	 */
	public void onSetupFromFEN(Game game, String fen);

	/**
	 * Handles a request to setup the position to the starting position.
	 */
	public void onSetupStartPosition(Game game);

	/**
	 * Handles a request to unexamine a game.
	 */
	public void onUnexamine(Game game);

	/**
	 * Handles a request to unobserve a game.
	 */
	public void onUnobserve(Game game);

	/**
	 * If word contains a likely channel, it is parsed out and returned.
	 */
	public String parseChannel(String word);

	/**
	 * If word contains a likely gameId, it is parsed out and returned.
	 */
	public String parseGameId(String word);

	/**
	 * If word contains a persons name, it is parsed out and returned.
	 */
	public String parsePerson(String word);

	/**
	 * TO DO: iron out what this really is.
	 */
	public void refreshGameScripts();

	/**
	 * Removes a connector listener from the connector.
	 */
	public void removeConnectorListener(ConnectorListener listener);

	/**
	 * TO DO: iron out what this really is.
	 */
	public void removeGameScript(GameScript script);

	/**
	 * Sends a message to the connector. A ChatEvent should be published with
	 * the text being sent in the connectors ChatService.
	 */
	public void sendMessage(String message);

	/**
	 * Sends a message to the connector. A ChatEvent of OUTBOUND type should
	 * only be published if isHidingFromUser is false.
	 */
	public void sendMessage(String message, boolean isHidingFromUser);

	/**
	 * Sends a message to the connector. A ChatEvent of OUTBOUND type should
	 * only be published containing the message if isHidingFromUser is false.
	 * The next message the connector reads in that is of the specified type
	 * should not be published to the ChatService.
	 */
	public void sendMessage(String message, boolean isHidingFromUser,
			ChatType hideNextChatType);

}
