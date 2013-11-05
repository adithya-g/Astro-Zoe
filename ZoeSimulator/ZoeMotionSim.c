#include <stdio.h>
#include <string.h>
#include <ipc.h>
#include <time.h>
#include <sys/time.h>
#include "ZoeMotionSim.h"
int counter = 1;
char id[20];
FILE *fp;
/* Function to create msg2 and publish a message called "ListeningToC"
*/
static void sendZoeResponse(drive_arc_command_msg_type msg1) {
    // Creating the response message
    drive_arc_response_msg_type msg2;
    msg2.command.radius = msg1.radius;
    msg2.command.time = msg1.time;
    msg2.command.speed = msg1.speed;
    strcpy(msg2.command.sender.data, msg1.sender.data);
    msg2.command.timestamp= msg1.timestamp;
    msg2.returnData.returnValue=1;
    if( (msg1.radius > 0 && msg1.radius <= 1000) && (msg1.speed >= (-1) && msg1.speed <= 1)) {
      msg2.returnData.isValid=1; 
    }
    strcpy(msg2.responseSender.data, id);
    gettimeofday(&msg2.timestamp, NULL);
    //Writing to log file
    fp=fopen("CommandLog.txt","a");
    fprintf(fp,"\nResponse Received Command Radius: %f , Speed: %f , Time: %f",msg2.command.radius,msg2.command.speed,msg2.command.time);
    fprintf(fp,"\tSender: %s",msg2.command.sender.data);
    time_t commandTime,responseTime;
    commandTime=msg2.command.timestamp.tv_sec;
    fprintf(fp,"\tTimestamp: %s", ctime(&commandTime));
    fprintf(fp,"Response Sender: %s",msg2.responseSender.data);
    responseTime=msg2.timestamp.tv_sec;
    fprintf(fp,"\tResponse Timestamp: %s\n", ctime(&commandTime));
    fclose(fp);
   IPC_publishData(MESSAGE_NAME_DRIVE_ARC_RESPONSE, &msg2); /*send the message*/
}
    
/* Message handler function - It gets called when we receive a message
 * with the name "ListeningToAndroid"
 */
static void msgHandler(MSG_INSTANCE msgRef, BYTE_ARRAY callData, void *clientData) {
    drive_arc_command_msg_type msg1;
    double radius, time, speed;
    char sender[20];
    time_type timestamp;
    IPC_unmarshallData(IPC_msgInstanceFormatter(msgRef), callData, &msg1, sizeof(msg1));
    fp=fopen("CommandLog.txt","a");
    fprintf(fp,"Command Radius: %f, Speed: %f, Time: %f",msg1.radius,msg1.speed,msg1.time);
    fprintf(fp,"\tSender: %s",msg1.sender.data);
    time_t commandTime=msg1.timestamp.tv_sec;
    fprintf(fp,"\tTimestamp: %s", ctime(&commandTime));
    fclose(fp);
    sendZoeResponse(msg1);
    IPC_freeByteArray(callData);
}
    
int main(int argc, char *argv[]) {
    if(argc < 2) {
        printf("Usage: %s <name>\n", argv[0]);
        return -1;
    }
    /*copy the id to the global variable so that it could be used later in the handler*/
    strncpy(id, argv[1], 20); 
    /* connect to IPC server */
    IPC_connect(id);
    /*Check if the following message format is already defined in central*/
    IPC_defineFormat(MESSAGE_NAME_DRIVE_ARC_COMMAND, MESSAGE_FMT_1); 
    /*Define the message*/
    IPC_defineMsg(MESSAGE_NAME_DRIVE_ARC_COMMAND, IPC_VARIABLE_LENGTH, MESSAGE_FMT_1);
    IPC_defineFormat(MESSAGE_NAME_DRIVE_ARC_RESPONSE, MESSAGE_FMT_2); 
    /*Define the message*/
    IPC_defineMsg(MESSAGE_NAME_DRIVE_ARC_RESPONSE, IPC_VARIABLE_LENGTH, MESSAGE_FMT_2);
    /*Register the function that will get invoked when this message is published by someone */ 
    IPC_subscribe(MESSAGE_NAME_DRIVE_ARC_COMMAND, msgHandler, id);
    /*IPC wait and spin*/
    IPC_dispatch();
    return 0;
}
