import java.lang.*;
import java.io.*;

class TerminationException extends Exception {
}

public class LOA_Q {
    GameClient client;
    WorkBoard board = new WorkBoard();
    int depth;
    
    public LOA_Q(GameClient client, int depth) {
	this.client = client;
	this.depth = depth;
    }

    private void make_my_move()
      throws TerminationException {
	board.bestMove(depth);
	int result = board.try_move(board.best_move);
	if (result == board.ILLEGAL_MOVE)
	    throw new Error("attempted illegal move");
	int state;
	try {
	    state = client.make_move(board.best_move);
	} catch(IOException e) {
	    e.printStackTrace(System.out);
	    throw new Error("move refused by referee");
	}
	if (result == board.GAME_OVER && state != client.STATE_DONE ||
	    result == board.CONTINUE && state != client.STATE_CONTINUE)
	    throw new Error("client and board disagree on sent move outcome");
	if (result == board.GAME_OVER)
	    throw new TerminationException();
    }

    private void get_opp_move()
      throws TerminationException {
	int state;
	try {
	    state = client.get_move();
	} catch(IOException e) {
	    e.printStackTrace(System.out);
	    throw new Error("couldn't get move from referee");
	}
	int result = board.try_move(client.move);
	if (result == board.ILLEGAL_MOVE)
	    throw new Error("received illegal move");
	if (result == board.GAME_OVER && state != client.STATE_DONE ||
	    result == board.CONTINUE && state != client.STATE_CONTINUE)
	    throw new Error("client and board disagree on received move outcome");
	if (result == board.GAME_OVER)
	    throw new TerminationException();
    }

    public void play() {
	try {
	    while (true) {
		if (client.who == client.WHO_BLACK)
		    make_my_move();
		else
		    get_opp_move();
		if (client.who == client.WHO_WHITE)
		    make_my_move();
		else
		    get_opp_move();
	    }
	} catch(TerminationException e) {
	    System.out.print("Game ends with ");
	    switch (client.winner) {
	    case client.WHO_WHITE:
		System.out.println("white win");
		break;
	    case client.WHO_BLACK:
		System.out.println("black win");
		break;
	    case client.WHO_NONE:
		System.out.println("draw");
		break;
	    }
	}
    }

    public static void main(String args[])
      throws IOException {
	if (args.length != 4)
	    throw new IllegalArgumentException(
	       "usage: black|white hostname server-number depth");
	int side;
	if (args[0].equals("black"))
	    side = GameClient.WHO_BLACK;
	else if (args[0].equals("white"))
	    side = GameClient.WHO_WHITE;
	else
	    throw new IllegalArgumentException("unknown side");
	String host = args[1];
	int server = Integer.parseInt(args[2]);
	GameClient client = new GameClient(side, host, server);
	int depth = Integer.parseInt(args[3]);
	LOA_Q game = new LOA_Q(client, depth);
	game.play();
    }
}
