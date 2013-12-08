package battleship;

public class HumanPlayer 
{
	public class HumanPlayer extends Player {
		protected String name;
		protected ArrayList<Integer> scores;

		HumanPlayer(String n, int c) {
			super(c);
			name = n;
			color = c;
			scores = new ArrayList<Integer>();
		}

		// Initialize from stream
		HumanPlayer(DataInputStream in) throws IOException {
			this(in.readUTF(), in.readInt());
			int numScores = in.readInt();
			scores = new ArrayList<Integer>(numScores);
			for (int i = 0; i < numScores; i++) {
				scores.add(i);
			}
		}

		public void write(DataOutputStream out) throws IOException {
			out.writeUTF(name);
			out.writeInt(color);
			out.writeInt(scores.size());
			for (Integer score : scores) {
				out.writeInt(score);
			}
		}
		
		public String getName() {
			return name;
		}
		
		public void makeMove() {

		}

		public void mouseMoved(int x) {
			current.setX(x);
		}

		public void mouseClicked() {
			if (!grid.getFullColumns().contains(current.getX())) {
				current.drop();
			}
		}
	}

}
