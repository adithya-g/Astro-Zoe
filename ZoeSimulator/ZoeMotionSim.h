#ifndef _ZOE_MOTIONCONTROL_H_
#define _ZOE_MOTIONCONTROL_H_
    
typedef struct sender_type {
    char data[20];
} sender_type;
    
typedef struct timeval time_type;
    
typedef struct generic_return_data_type {
    int returnValue; //Did the command succeed?
    int isValid; //Was the command valid?
} generic_return_data_type;
    
/* 
 * Drive Arc
 *
 * From Navigator To Vehicle Controller
 *
 * This command will move the rover along an arc of the specified
 * radius. The Vehicle Controller will not initiate the arc if
 * the software stop has been set.
 */
typedef struct drive_arc_command_msg_type {
    double radius;      /* Arc radius (1000 = straight) The Vehicle Controller
			   verifies this doesn't violate the minimum radius */
    double speed;       /* Rover speed (positive = forward, negative =
			   reverse) The Vehicle Controller verifies this
    			   doesn't violate the maximum speed. */
    double time;        /* Duration, future arcs will supercede the current one.
    			   Note that the duration should be short, if the VC
    			   stops responding the rover will not stop until the
    			   current arc is finished. */
    sender_type sender; /* Name of the sending module. */
    time_type timestamp;
} drive_arc_command_msg_type;
    
#define MESSAGE_NAME_DRIVE_ARC_COMMAND "driveArcCommand"
#define MESSAGE_FMT_1 "{double, double, double, {[char:20]}, {long, long}}"
    
typedef struct drive_arc_response_msg_type {
    drive_arc_command_msg_type command;
    generic_return_data_type returnData;
    sender_type responseSender;
    time_type timestamp;
} drive_arc_response_msg_type;
    
#define MESSAGE_NAME_DRIVE_ARC_RESPONSE "driveArcResponse"
#define MESSAGE_FMT_2 "{{double, double, double, {[char:20]}, {long, long}}, {int, int}, {[char:20]}, {long, long}}"
    
static const char* drive_arc_command_msg_name = "driveArcCommand";
static const char* drive_arc_response_msg_name = "driveArcResponse";
    
#endif //_ZOE_MOTIONCONTROL_H_
