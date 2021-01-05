import java.util.*;

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
	int deadline;
	
	
	protected Assignment() {
	}
	
	protected Assignment(int number, int weight, int deadline) {
		this.number = number;
		this.weight = weight;
		this.deadline = deadline;
	}
	
	
	
	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 * Return -1 if a1 > a2
	 * Return 1 if a1 < a2
	 * Return 0 if a1 = a2 
	 */
	@Override
	public int compare(Assignment a1, Assignment a2) {
		// TODO Implement this
		if (a1.weight<a2.weight){
			return 1;
		}else if(a1.weight>a2.weight){
			return -1;
		}else {
			//return 0;
			if (a1.deadline<a2.deadline){
				return -1;
			}else if (a1.deadline>a2.deadline){
				return 1;
			}else {
				return 0;
			}
		}
		
		
		//return 0;
	}
}

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
	int lastDeadline = 0;
	
	protected HW_Sched(int[] weights, int[] deadlines, int size) {
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m =size;
	}
	
	
	/**
	 * 
	 * @return Array where output[i] corresponds to the assignment 
	 * that will be done at time i.
	 */
	public int[] SelectAssignments() {
		//TODO Implement this
		
		//Sort assignments
		//Order will depend on how compare function is implemented
		Collections.sort(Assignments, new Assignment());
		
		// If schedule[i] has a value -1, it indicates that the 
		// i'th timeslot in the schedule is empty
		int[] schedule = new int[lastDeadline];
		for (int i=0; i < schedule.length; ++i) {
			schedule[i] = -1;
		}
		int remaining = lastDeadline;
		for (int i =0;i<Assignments.size();i++){
			int ddlb = Assignments.get(i).deadline;
			if (schedule[ddlb-1]==-1){
				schedule[ddlb-1]= Assignments.get(i).number;
				remaining--;
			}else{
				for(int j = ddlb-2;j>=0;j--){
					if (schedule[j]==-1){
						schedule[j]=Assignments.get(i).number;
						//used++;
						remaining--;
						break;
					}
				}
				if (remaining == 0){
					return schedule;
				}
			}
			//return schedule;
		}
		
		
		
		
	
		
		return schedule;
	}
}
	



