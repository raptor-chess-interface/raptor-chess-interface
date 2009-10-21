package raptor.script;

/**
 * This interface contains the core Raptor features you have access to in
 * scripting. If you would like new ones added you can make enhancement requests
 * on the request page or post in the Google groups page.
 */
public interface ScriptContext {

	/**
	 * Displays an alert message to the user.
	 */
	public void alert(String message);

	/**
	 * Some scripts use parameters. This will return the parameters if any were
	 * passed in.
	 * 
	 * @return The parameters. An empty String[] if there are no parameters.
	 */
	public String[] getParameters();

	/**
	 * Returns the current ping time in milliseconds.
	 */
	public long getPingMillis();

	/**
	 * Returns the current user you are following, if you are not following
	 * anyone null is returned.
	 */
	public String getUserFollowing();

	/**
	 * Returns the number of seconds you have been idle, i.e. have not sent any
	 * messages.
	 */
	public int getUserIdleSeconds();

	/**
	 * Returns the logged in user name.
	 */
	public String getUserName();

	/**
	 * Retrieves a stored value.
	 * 
	 * @param key
	 *            The key to retrieve.
	 * @return May return null if there is no value stored.
	 */
	public String getValue(String key);

	/**
	 * Launches the process with the specified arguments. This can be used to
	 * tie external scripts to Raptor.
	 * 
	 * @param commandAndArgs
	 *            can be just a command name or you can pass in the command and
	 *            multiple arguments i.e.
	 *            launchProcess("say","this is the text to say").
	 */
	public void launchProcess(String... commandAndArgs);

	/**
	 * Opens a channel tab for the specified channel if one is not already
	 * opened.
	 */
	public void openChannelTab(String channel);

	/**
	 * Opens a partner, ptell tab, if one is not already opened.
	 */
	public void openPartnerTab();

	/**
	 * Opens a person tab if one is not already open for the specified person.
	 */
	public void openPersonTab(String person);

	/**
	 * Opens a regex tab if one is not already open.
	 */
	public void openRegExTab(String regularExpression);

	/**
	 * Opens the specified url.
	 */
	public void openUrl(String url);

	/**
	 * Plays the specified bughouse sound.
	 * 
	 * @param soundName
	 *            The name of the sound in the resources/sound/bughouse
	 *            directory without the .wav. Spaces should be included if they
	 *            are in the file name.
	 */
	public void playBughouseSound(String soundName);

	/**
	 * Plays the specified sound.
	 * 
	 * @param soundName
	 *            The name of the sound in the resources/sound directory without
	 *            the .wav.
	 */
	public void playSound(String soundName);

	/**
	 * Prompts the user for text with the specified message. The text the user
	 * types in is returned.
	 * 
	 * @param message
	 *            The message used to prompt the user.
	 * @return
	 */
	public String prompt(String message);

	/**
	 * Sends the specified message to the connector.
	 * 
	 * @param message
	 *            The message to send.
	 */
	public void send(String message);

	/**
	 * Sends the specified message to the connector and hides the message from
	 * the user.
	 * 
	 * @param message
	 *            The message to send.
	 */
	public void sendHidden(String message);

	/**
	 * Displays a message to the user in the chat console. The message will not
	 * be sent to the connector.
	 * 
	 * @param message
	 *            The message to display to the user.
	 */
	public void sendToConsole(String message);

	/**
	 * Speaks the specified message. Requires that you have speech setup. OS X
	 * users have speech enabled automatically; however, other users must
	 * configure speech in the Speech preferences.
	 */
	public void speak(String message);

	/**
	 * Stores a value which can be obtained later in another script.
	 * 
	 * @param key
	 *            The key of the value to store.
	 * @param value
	 *            The value to store.
	 */
	public void storeValue(String key, String value);

	/**
	 * MIME encodes the passed in string.
	 * 
	 * @param stringToEncode
	 *            The string to encode.
	 * @return The encoded string.
	 */
	public String urlEncode(String stringToEncode);

}