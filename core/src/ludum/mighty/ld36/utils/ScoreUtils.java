package ludum.mighty.ld36.utils;

public class ScoreUtils implements Comparable<ScoreUtils> {

	String name;
	int score;

	public ScoreUtils(String name, int score) {
		this.name = name;
		this.score = score;
	}

	@Override
	public int compareTo(ScoreUtils arg0) {
		// TODO Auto-generated method stub

		return this.score - arg0.score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
