package server;

import ocsf.server.*;

import java.io.*;
import java.util.Scanner;

import common.*;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * 

import client.*;

e is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version September 2020
 */
public class ChatServer extends AbstractServer
{
	final public static int DEFAULT_PORT = 5555;
  public ChatServer(int port){
    super(port);
  }

  /**
   * Implementation of the hook method called each time a new client connection is
   * accepted. The default implementation does nothing.
   * @param client the connection connected to the client.
   */
  @Override
  protected void clientConnected(ConnectionToClient client) {
	  System.out.println("Client has connected!");
  }
  /**
   * Implementation of the hook method called each time a client disconnects.
   * The default implementation does nothing. The method
   * may be overridden by subclasses but should remains synchronized.
   *
   * @param client the connection with the client.
   */
  @Override
  synchronized protected void clientDisconnected(ConnectionToClient client) {
	 //this.sendToAllClients("Client has disconnected!");
	  System.out.println("Client has disconnected!");
	 
  }
  /**
   * Implementation of the hook method called each time an exception is thrown in a
   * ConnectionToClient thread.
   * The method may be overridden by subclasses but should remains
   * synchronized.
   *
   * @param client the client that raised the exception.
   * @param Throwable the exception thrown.
   */
  @Override
  synchronized protected void clientException(
    ConnectionToClient client, Throwable exception) {
	  //this.sendToAllClients("Client disconnected");
	  System.out.println("Client has disconnected!");
  }

   /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
    System.out.println("Message received: " + msg + " from " + client);
    this.sendToAllClients(msg);
    
  }

  public void handleMessageFromServerConsole(String message) {
	  this.sendToAllClients(message);
  }
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }

  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }

}
  