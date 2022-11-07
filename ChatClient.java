// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
	  //clientUI.display("Reached here before handle message from client ui");
	 // System.out.println("Reached here before handle message from client ui");
    try
    {
    	if(message.startsWith("#")) {
    		//clientUI.display("Reached here before hashtag");
    		handleCommand(message);
    		
    	}
    	else {
    		//clientUI.display("Reached here before send to server");
      sendToServer(message);
    	}
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  
  public void handleCommand(String cmd) {
	  String[] commandParameter = cmd.split(" ",2);
	 
	  if(cmd.equals("#quit")) {
		  clientUI.display("QUIT command entered. Client will Quit!");
		  quit();
	  }
	  else if(cmd.equals("#logoff")){
		  try {
			closeConnection();
		} catch (IOException e) {
			System.out.println("Error : Cannot log off!");
		}
	  }
	  else if(cmd.equals("#sethost")) {
		  if(this.isConnected()) {
			  System.out.println("Error : Client is already connected");
		  }
		  else 
		  {
			  String hostParameter = commandParameter[1];
			  this.setHost(hostParameter);
			  System.out.println("New host set");
		  }
	  }
	  else if(cmd.equals("#setPort")) {
		  if(this.isConnected()) {
			  System.out.println("Error : Client is already connected");
		  }
		  else {
			  String hostParameter = commandParameter[1];
			  this.setPort(Integer.parseInt(hostParameter));
		  }
	  }
	  else if(cmd.equals("#login")) {
		  if(this.isConnected()) {
			  System.out.println("Error : Client is already connected");
		  }else {
			  try {
				  this.openConnection();
			  } catch (IOException e) {
				  System.out.println("Error : Cannot open connection!");
			  }
		  }
	  }
	  else if(cmd.equals("#gethost")) {
		  System.out.println("The host is " + this.getHost());
	  }
	  else if(cmd.equals("#getport")) {
		  System.out.println("The port is " + this.getPort());
	  }
	  else {
		  System.out.println("The command you've entered is wrong!");
	  }
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  
  /**
	 * Implementation of the hook method called after the connection has been closed. The default
	 * implementation does nothing. The method may be overriden by subclasses to
	 * perform special processing such as cleaning up and terminating, or
	 * attempting to reconnect.
	 */
  	@Override
	protected void connectionClosed() {
  		clientUI.display("The connection has been closed!");
	}

	/**
	 * Implementation of the hook method called each time an exception is thrown by the client's
	 * thread that is waiting for messages from the server. The method may be
	 * overridden by subclasses.
	 * 
	 * @param exception
	 *            the exception raised.
	 */
  	@Override
	protected void connectionException(Exception exception) {
  		clientUI.display("The server has stopped!");
	}
}
//End of ChatClient class
