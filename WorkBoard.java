import java.util.Vector;

public class WorkBoard extends Board {
    static final int INF = 8 * 12 + 1;
    Move best_move = null;

    public WorkBoard() {
    }

    public WorkBoard(WorkBoard w) {
	super(w);
    }

    int countMoves(int i, int j) {
	int n = 0;
	for (int dx = -1; dx <= 1; dx++)
	    for (int dy = -1; dy <= 1; dy++) {
		if (dx == 0 && dy == 0)
		    continue;
		int d = dist(i, j, dx, dy);
		Move m = new Move(i, j, i + d * dx, j + d * dy);
		if (move_ok(m))
		    n++;
	    }
	return n;
    }

    int heval() {
	int nmoves = 0;
	int omoves = 0;
	for (int i = 0; i < 8; i++)
	    for (int j = 0; j < 8; j++)
		if (square[i][j] == checker_of(to_move))
		    nmoves += countMoves(i, j);
	        else if (square[i][j] == checker_of(opponent(to_move)))
		    omoves += countMoves(i, j);
	return nmoves - omoves;
    }

    int negamax(int depth) {
	if (depth <= 0)
	    return heval();
	Vector moves = genMoves();
	int maxv = -INF;
	Move maxm = null;
	for (int i = 0; i < moves.size(); i++) {
	    Move m = (Move)moves.get(i);
	    int capture = square[m.x2][m.y2];
	    int saved_to_move = to_move;
	    int status = try_move(m);
	    if (status == ILLEGAL_MOVE)
		throw new Error("unexpectedly illegal move");
	    if (status == GAME_OVER) {
		if (to_move == saved_to_move) {
		    maxv = INF;
		    maxm = m;
		}
		else if (to_move == OBSERVER && maxv < 0) {
		    maxv = 0;
		    maxm = m;
		} else if (to_move == opponent(saved_to_move) &&
			   maxv == -INF) {
		    maxm = m;
		} else
		    throw new Error("winner error");
	    } else {
		int v = -negamax(depth - 1);
		if (v >= maxv) {
		    maxv = v;
		    maxm = m;
		}
	    }
	    to_move = saved_to_move;
	    square[m.x2][m.y2] = capture;
	    square[m.x1][m.y1] = checker_of(to_move);
	    predecessor = predecessor.predecessor;
	}
	best_move = maxm;
	return maxv;
    }

    void bestMove(int depth) {
	int v = negamax(depth);
    }
}
