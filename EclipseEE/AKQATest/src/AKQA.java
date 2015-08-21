import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;

public class AKQA {

	// Map for final meeting schedules
	static HashMap<String, ArrayList<Meeting>> map = new HashMap<String, ArrayList<Meeting>>();

	public static void main(String[] args) {
		// Init scanner. Provide your input file or enter the input form the console
//		Scanner input = new Scanner(System.in);
		Scanner input = null;
		try {
			input = new Scanner(new File("/Users/compass/Downloads/input.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Configure Office time start & end
		String line = input.nextLine();
		int timeOfficeStartHour = Integer.parseInt(line.substring(0,2));
		int timeOfficeStartMin = Integer.parseInt(line.substring(2,4));
		int timeOfficeEndHour = Integer.parseInt(line.substring(5,7));
		int timeOfficeEndMin = Integer.parseInt(line.substring(7,9));
	    
	    // List of all meeting schedule request
	    List<Meeting> meetings = new ArrayList<Meeting>(); 
	    
	    // Parse and store all schedule submission
	    String timePatternSubmission = "yyyy-MM-dd HH:mm:ss";
	    String timePatternMeeting = "yyyy-MM-dd HH:mm";
	    DateTime timeSubmitted, timeForMeeting;
	    String employee;
	    Meeting newMeeting = null;
	    int duration;
		while(input.hasNextLine()) {
			// Obtain info
			line = input.nextLine();
		    timeSubmitted  = DateTime.parse(line.substring(0,19), DateTimeFormat.forPattern(timePatternSubmission));
			employee = line.substring(20,26);
			line = input.nextLine();
			timeForMeeting  = DateTime.parse(line.substring(0,16), DateTimeFormat.forPattern(timePatternMeeting));
			duration = Integer.parseInt(line.substring(17).trim());
			newMeeting = new Meeting(timeSubmitted, employee, timeForMeeting, timeForMeeting.plusHours(duration));
			meetings.add(newMeeting);
		}
		
		// Sort the request according to request submission time
		Collections.sort(meetings, new MeetingComparator());
		
		// Put meeting schedule in to a hash map
		for (Meeting nextMeeting : meetings) {
			// Add meeting if possible
			String meetingDateStr = nextMeeting.getMeetingDate();
			ArrayList<Meeting> meetingsInADay = map.get(meetingDateStr);
			if (meetingsInADay == null) {
				meetingsInADay = new ArrayList<Meeting>();
				map.put(meetingDateStr, meetingsInADay);
			}
			// Check if it goes over the office hour			
			boolean isInOfficeHour = false;
			if (	(nextMeeting.getTimeToStart().getHourOfDay()>=timeOfficeStartHour
					&&nextMeeting.getTimeToStart().getMinuteOfHour()>=timeOfficeStartMin)
					&&
					(nextMeeting.getTimeToEnd().getHourOfDay()<=timeOfficeEndHour
					&&nextMeeting.getTimeToEnd().getMinuteOfHour()<=timeOfficeEndMin)
					) {
				isInOfficeHour = true;
			}			
			// Check if there's a previous meeting that overlaps
			boolean hasNoConflict = true;
			for (Meeting meetingScheduled : meetingsInADay) {
				if (nextMeeting.getMeetingInterval().overlaps(meetingScheduled.getMeetingInterval())) {
					hasNoConflict = false;
					break;
				}
			}
			// Add meeting if possible
			if (isInOfficeHour&&hasNoConflict) {
				meetingsInADay.add(nextMeeting);
			}
		}

		// Output
		ArrayList<String> dates = new ArrayList<String>(map.keySet());
		Collections.sort(dates);
		for (String date : dates) {
			// Print Date
			System.out.println(date);
			// Print scheduled meetings
			ArrayList<Meeting> meetingScheduled = map.get(date);
			for (Meeting meet : meetingScheduled) {
				System.out.println(meet.getOutputDateStr());
			}
		}
		
		
	}
}

class Meeting{
	private DateTime timeRequested;
	private String employee;
	private DateTime timeToStart;
	private DateTime timeToEnd;
	private Interval interval;
	
	public Meeting(DateTime timeReq, String employee, DateTime timeStart, DateTime timeEnd) {
		this.timeRequested = timeReq;
		this.employee = employee;
		this.timeToStart = timeStart;
		this.timeToEnd = timeEnd;
		this.interval = new Interval(timeStart, timeEnd);
	}

	public DateTime getTimeRequested() {
		return this.timeRequested;
	}
	
	public String getMeetingDate() {
		String timePatternDate = "yyyy-MM-dd";
		return this.timeToStart.toString(timePatternDate);
	}
	
	public Interval getMeetingInterval() {
		return this.interval;
	}
	
	public DateTime getTimeToStart() {
		return this.timeToStart;
	}
	
	public DateTime getTimeToEnd() {
		return this.timeToEnd;
	}
	
	public String getOutputDateStr() {
		return this.timeToStart.toString("HH:mm") + " " 
				+ this.timeToEnd.toString("HH:mm") + " "
				+ this.employee;
	}
}

class MeetingComparator implements Comparator<Meeting> {
	@Override
	public int compare(Meeting m1, Meeting m2) {
		if (m1.getTimeRequested().isBefore(m2.getTimeRequested())) {
			return -1;
		} else if (m1.getTimeRequested().equals(m2.getTimeRequested())) {
			return 0;
		} else {
			return 1;
		}
	}
}
