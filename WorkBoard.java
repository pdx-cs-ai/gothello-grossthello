import java.util.Vector;

public class WorkBoard extends Board {
    static final int INF = 5 * 5 + 1;
    Move best_move = null;

    public WorkBoard() {
    }

    public WorkBoard(WorkBoard w) {
	super(w);
    }

    int heval() {
	int nstones = 0;
	int ostones = 0;
	for (int i = 0; i < 5; i++)
	    for (int j = 0; j < 5; j++)
		if (square[i][j] == checker_of(to_move))
		    nstones++;
	        else if (square[i][j] == checker_of(opponent(to_move)))
		    ostones++;
	return nstones - ostones;
    }

    int negamax(int depth) {
	if (depth <= 0)
	    return heval();
	Vector moves = genMoves();
	int maxv = -INF;
	Move maxm = null;
	for (int i = 0; i < moves.size(); i++) {
	    Move m = (Move)moves.get(i);
	    /* XXX do-undo is very difficult here, since we
	       may capture a large number of stones.  For
	       now, just punt. */
	    WorkBoard scratch = new WorkBoard(this);
	    int status = scratch.try_move(m);
	    if (status == ILLEGAL_MOVE)
		throw new Error("unexpectedly illegal move");
	    if (status == GAME_OVER) {
		int winner = scratch.referee();
		if (winner == to_move) {
		    maxv = INF;
		    maxm = m;
		} else if (winner == OBSERVER) {
		    if (maxv < 0) {
			maxv = 0;
			maxm = m;
		    }
		} else if (winner == opponent(to_move)) {
		    if (maxv == -INF)
			maxm = m;
		} else
		    throw new Error("winner error");
	    } else {
		int v = -scratch.negamax(depth - 1);
		if (v >= maxv) {
		    maxv = v;
		    maxm = m;
		}
	    }
	}
	best_move = maxm;
	return maxv;
    }

    void bestMove(int depth) {
	int v = negamax(depth);
    }
}
