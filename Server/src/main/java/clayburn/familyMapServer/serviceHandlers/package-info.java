/**
 * Provides classes that handle requests to the server API. The possible requests are as follows:
 * <ul>
 *     <li>Register User (/user/register)</li>
 *     <li>Login (/user/login)</li>
 *     <li>Clear database (/clear)</li>
 *     <li>Generate family tree (/fill/[username]/{generations})</li>
 *     <li>Load data to server (/load)</li>
 *     <li>Get person info (/person/[personID])</li>
 *     <li>Get all people info (/person)</li>
 *     <li>Get event info (/event/[eventID])</li>
 *     <li>Get all event info (/event)</li>
 * </ul>
 */
package clayburn.familyMapServer.serviceHandlers;
