import java.io.*;
import java.util.ArrayList;

public class Config {
	private File file;
	private ArrayList<HumanPlayer> players;

	public Config(File f) {
		file = f;
		read();
	}

	public ArrayList<HumanPlayer> getPlayers() {
		return new ArrayList<HumanPlayer>(players);
	}

	public void addPlayer(HumanPlayer player) {
		players.add(player);
		write();
	}

	public void removePlayer(HumanPlayer player) {
		players.remove(player);
		write();
	}

	public void removeAllPlayers() {
		players.clear();
		write();
	}

	public void write() {
		try {
			file.createNewFile();
			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			out.writeUTF("battleship"); //Header

			// Write number of players
			out.writeInt(players.size());

			// Write all the players
			for (HumanPlayer p : players) {
				p.write(out);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

		}
	}

	public void read() {
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			// Corruption check
			if (!in.readUTF().equals("battleship")) {
				in.close();
				throw new IOException("Bad header");
			}
			int numPlayers = in.readInt();
			players = new ArrayList<HumanPlayer>(numPlayers);
			for (int i = 0; i < numPlayers; i++) {
				players.add(new HumanPlayer(in));
			}
			in.close();
		} catch (IOException e) {
			players = new ArrayList<HumanPlayer>();
			write();
		}
	}
}
