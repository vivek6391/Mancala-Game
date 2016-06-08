import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class mancala 
{
	public static void main (String[] arg) throws Exception
	{
		try
		{
			Board w_manacalaBoard = new Board();
			
			w_manacalaBoard.fillBoard(arg[1]);
			
			w_manacalaBoard.playMancala();			
		}
		catch (Exception a_ex)
		{
			a_ex.printStackTrace();
		}
	}
}

class Node
{
	private int m_playingPlayer = 0;
	private int[] m_mancalaBoard = null;
	private int m_bestValueFromChild = Integer.MAX_VALUE;
	private int m_nodeAlphaValue = Integer.MIN_VALUE;
	private int m_nodeBetaValue = Integer.MAX_VALUE;
	private String m_nodeName = null;
	private int m_task = 0;
	private int m_playerNo = 0;
	private int m_cutOffDepth = 0;
	private int m_stonesInPlayer1Index = 0;
	private int m_stonesInPlayer2Index = 0;
	private int m_player1StartIndex = 0;
	private int m_player1EndIndex = 0;
	private int m_player2StartIndex = 0;
	private int m_player2EndIndex = 0;
	private int m_numberofMancalaHoles = 0;
	private int[] m_finalBoard = null;
	private boolean isBoardEmpty = false;

	private ArrayList<String> m_instructions = new ArrayList<String>();
	

	public void setIsBoardEmpty(boolean a_isBoardEmpty)
	{
		this.isBoardEmpty = a_isBoardEmpty;
	}
	
	public boolean isEmptyBoard()
	{
		return this.isBoardEmpty;
	}
	
	public void setAlphaValue(int a_alphaValue) {
		this.m_nodeAlphaValue = a_alphaValue;
	}
	
	public void setBetaValue(int a_betaValue) {
		this.m_nodeBetaValue = a_betaValue;
	}
	
	public int getAlphaValue() {
		return this.m_nodeAlphaValue;
	}
	
	public int getBetaValue() {
		return this.m_nodeBetaValue;
	}
	
	public String getAlphaValueString()
	{
		if (m_nodeAlphaValue == Integer.MAX_VALUE)
			return "Infinity";
		else if (m_nodeAlphaValue == Integer.MIN_VALUE)
				return "-Infinity";
			else
				return String.valueOf(m_nodeAlphaValue);
	}
	
	public String getBetaValueString()
	{
		if (m_nodeBetaValue == Integer.MAX_VALUE)
			return "Infinity";
		else if (m_nodeBetaValue == Integer.MIN_VALUE)
				return "-Infinity";
			else
				return String.valueOf(m_nodeBetaValue);
	}
	
	public int[] getFinalBoard() {
		return m_finalBoard;
	}

	public void setFinalBoard(int[] mancalaBoard) {
		this.m_finalBoard = mancalaBoard;
	}
	
	public String getValueString()
	{
		if (m_bestValueFromChild == Integer.MAX_VALUE)
			return "Infinity";
		else if (m_bestValueFromChild == Integer.MIN_VALUE)
				return "-Infinity";
			else
				return String.valueOf(m_bestValueFromChild);
	}
	public void setPlayingPlayer(int a_playerNo)
	{
		this.m_playingPlayer = a_playerNo;
	}
	
	public int getValueFromChild()
	{
		return m_bestValueFromChild;
	}
	
	public void setValueFromChild(int a_valueChild)
	{
		m_bestValueFromChild = a_valueChild;
	}
	
	public void setManacalaHoles(int a_manacalaHoles)
	{
		m_numberofMancalaHoles = a_manacalaHoles;
	}
	
	public int getManacalaHoles()
	{
		return m_numberofMancalaHoles;
	}
	public void setNodeName(String a_nodeName)
	{
		m_nodeName = a_nodeName;
	}
	
	public String getNodeName()
	{
		return m_nodeName;
	}
	
	public int getPlayer1StartIndex() {
		return m_player1StartIndex;
	}

	public void setPlayer1StartIndex(int player1StartIndex) {
		m_player1StartIndex = player1StartIndex;
	}

	public int getPlayer1EndIndex() {
		return m_player1EndIndex;
	}

	public void setPlayer1EndIndex(int player1EndIndex) {
		m_player1EndIndex = player1EndIndex;
	}

	public int getPlayer2StartIndex() {
		return m_player2StartIndex;
	}

	public void setPlayer2StartIndex(int player2StartIndex) {
		m_player2StartIndex = player2StartIndex;
	}

	public int getPlayer2EndIndex() {
		return m_player2EndIndex;
	}

	public void setPlayer2EndIndex(int player2EndIndex) {
		m_player2EndIndex = player2EndIndex;
	}

	public ArrayList<String> getInstructions() {
		return m_instructions;
	}

	public void setInstructions(ArrayList<String> instructions) {
		this.m_instructions = instructions;
	}

	public void addInstructions(String a_instr) {
		this.m_instructions.add(a_instr);
	}
	public int[] getMancalaBoard() {
		return m_mancalaBoard;
	}

	public void setMancalaBoard(int[] mancalaBoard) {
		this.m_mancalaBoard = mancalaBoard;
	}

	public int getTask() {
		return m_task;
	}

	public void setTask(int m_task) {
		this.m_task = m_task;
	}

	public int getPlayer() {
		return m_playerNo;
	}

	public void setPlayer(int a_player) {
		this.m_playerNo = a_player;
	}

	public int getCutOffDepth() {
		return m_cutOffDepth;
	}

	public void setCutOffDepth(int m_cutOffDepth) {
		this.m_cutOffDepth = m_cutOffDepth;
	}

	public int getPlayer1MancalaIndex() {
		return m_stonesInPlayer1Index;
	}

	public void setStonesInPlayer1Index(int m_stonesInPlayer1Index) {
		this.m_stonesInPlayer1Index = m_stonesInPlayer1Index;
	}

	public int getPlayer2MancalaIndex() {
		return m_stonesInPlayer2Index;
	}

	public void setStonesInPlayer2Index(int m_stonesInPlayer2Index) {
		this.m_stonesInPlayer2Index = m_stonesInPlayer2Index;
	}
	
	public int getEvaluationFunctionValue() throws Exception
	{
		if (m_playingPlayer == 1)
			return m_mancalaBoard[m_stonesInPlayer1Index] - m_mancalaBoard[m_stonesInPlayer2Index];
		return m_mancalaBoard[m_stonesInPlayer2Index] - m_mancalaBoard[m_stonesInPlayer1Index];
	}
	
	public Node toCopyNode() throws Exception
	{
		Node w_node = new Node();
		
		int[] w_dest = new int[m_mancalaBoard.length];
		System.arraycopy( m_mancalaBoard, 0, w_dest, 0, m_mancalaBoard.length );
		w_node.setMancalaBoard(w_dest);
		w_node.setPlayer(m_playerNo);
		w_node.setStonesInPlayer1Index(m_stonesInPlayer1Index);
		w_node.setStonesInPlayer2Index(m_stonesInPlayer2Index);
		w_node.setTask(m_task);
		w_node.setCutOffDepth(m_cutOffDepth);
		w_node.setPlayer1StartIndex(m_player1StartIndex);
		w_node.setPlayer1EndIndex(m_player1EndIndex);
		w_node.setPlayer2StartIndex(m_player2StartIndex);
		w_node.setPlayer2EndIndex(m_player2EndIndex);
		w_node.setNodeName(m_nodeName);
		w_node.setManacalaHoles(m_numberofMancalaHoles);
		w_node.setValueFromChild(m_bestValueFromChild);
		w_node.setAlphaValue(m_nodeAlphaValue);
		w_node.setBetaValue(m_nodeBetaValue);
		w_node.setPlayingPlayer(m_playingPlayer);
		w_node.setInstructions(m_instructions);
		return w_node;
	}
	
	public String toString()
	{
		String w_value = "";
		for (int i = 0 ; m_mancalaBoard != null && i < m_mancalaBoard.length ; i++)
			w_value = w_value + "," + String.valueOf(m_mancalaBoard[i]);
		return (w_value.length() > 0 ? w_value.substring(1) : w_value) + "#" + m_nodeName;
	}
	
}

class Board
{
	private int m_task = 0;
	private int m_playerNo = 0;
	private int m_cutOffDepth = 0;
	private int m_stonesInPlayer1Index = 0;
	private int m_stonesInPlayer2Index = 0;
	private Node m_mancalaRoot = null;
	
	public void fillBoard(String a_fileName) throws Exception
	{
		BufferedReader w_bufReader = null;
		File w_file = null;
		FileReader w_fileReader = null;
		try
		{
			m_mancalaRoot = new Node();
			int[] w_mancalaBoard = null;
			w_file = new File(a_fileName);
			w_fileReader = new FileReader(w_file);
			w_bufReader = new BufferedReader(w_fileReader);
			
			m_task = Integer.parseInt(w_bufReader.readLine());
			m_playerNo = Integer.parseInt(w_bufReader.readLine());
			m_cutOffDepth = Integer.parseInt(w_bufReader.readLine());
			
			String w_line = w_bufReader.readLine();
			String[] w_player2 = w_line.split(" ");
			m_stonesInPlayer1Index = w_player2.length;
			int w_totalLength = w_player2.length * 2 + 2;
			m_stonesInPlayer2Index = w_totalLength - 1;
			w_mancalaBoard = new int[w_totalLength];
			
			int k = 0;
			for (int i = w_totalLength - 2 ; k < w_player2.length ; i--)
			{
				w_mancalaBoard[i] = Integer.parseInt(w_player2[k++]);
			}
			w_mancalaBoard[m_stonesInPlayer1Index] = 0;
			
			w_line = w_bufReader.readLine();
			String[] w_player1 = w_line.split(" ");
			
			for (int i = 0 ; i < w_player1.length ; i++)
			{
				w_mancalaBoard[i] = Integer.parseInt(w_player1[i]);
			}
			w_mancalaBoard[m_stonesInPlayer2Index] = Integer.parseInt(w_bufReader.readLine());
			w_mancalaBoard[m_stonesInPlayer1Index] = Integer.parseInt(w_bufReader.readLine());
			
			m_mancalaRoot.setMancalaBoard(w_mancalaBoard);
			m_mancalaRoot.setPlayer(m_playerNo);
			m_mancalaRoot.setStonesInPlayer1Index(m_stonesInPlayer1Index);
			m_mancalaRoot.setStonesInPlayer2Index(m_stonesInPlayer2Index);
			m_mancalaRoot.setTask(m_task);
			m_mancalaRoot.setCutOffDepth(m_cutOffDepth);
			m_mancalaRoot.setPlayer1StartIndex(0);
			m_mancalaRoot.setPlayer1EndIndex(w_player1.length - 1);
			m_mancalaRoot.setPlayer2StartIndex(w_totalLength - 2);
			m_mancalaRoot.setPlayer2EndIndex(w_totalLength - w_player1.length - 1);
			m_mancalaRoot.setManacalaHoles(w_player1.length);
			m_mancalaRoot.setPlayingPlayer(m_playerNo);
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}
		finally
		{
			if (w_bufReader != null)
				w_bufReader.close();
			if (w_fileReader != null)
				w_fileReader.close();
		}
	}
	
	public void playMancala() throws Exception
	{
		m_mancalaRoot.setNodeName(m_mancalaRoot.getPlayer() == 1 ? "B" : "A");
		if (m_task == 1)
			playManacalaGreedy(m_mancalaRoot);
		else if (m_task == 2)
				playMancalaMinMax(m_mancalaRoot);
		else if (m_task == 3)
			playByAlphaBetaMethod(m_mancalaRoot);
		
	}
	
	public static void printMancalaState(ArrayList<String> a_resultList) throws Exception
	{
		PrintWriter w_writer = null;
		try
		{
			w_writer = new PrintWriter("next_state.txt", "UTF-8");
			
			for (String w_result : a_resultList)
			{
				w_writer.println(w_result);
			}
			
			w_writer.close();
		}
		catch (Exception a_ex)
		{
			
		}
		finally
		{
			if (w_writer != null)
				w_writer.close();
		}
	}
	
	public static void printTraverseLog(ArrayList<String> a_resultList) throws Exception
	{
		PrintWriter w_writer = null;
		try
		{
			w_writer = new PrintWriter("traverse_log.txt", "UTF-8");
			
			for (String w_result : a_resultList)
			{
				w_writer.println(w_result);
			}
			
			w_writer.close();
		}
		catch (Exception a_ex)
		{
			
		}
		finally
		{
			if (w_writer != null)
				w_writer.close();
		}
	}
	
	public static void playMancalaMinMax(Node a_manacalaBoard) throws Exception
	{
		a_manacalaBoard.setNodeName("root");
		a_manacalaBoard.setValueFromChild(Integer.MIN_VALUE);
		
		a_manacalaBoard.addInstructions("Node,Depth,Value");
		maxValueFunction(a_manacalaBoard, false, false, 0, a_manacalaBoard.getPlayer());
		
		a_manacalaBoard.setMancalaBoard(a_manacalaBoard.getFinalBoard());
		printMancalaState(getStateOfMancalaBoard(a_manacalaBoard));
		printTraverseLog(a_manacalaBoard.getInstructions());
	}
	
	
	public static ArrayList<String> getStateOfMancalaBoard(Node a_manacalaBoard) throws Exception
	{
		ArrayList<String> w_outputList = new ArrayList<String>();
		
		StringBuffer w_newBuffer = new StringBuffer();
		int[] w_manacalaBoard = a_manacalaBoard.getMancalaBoard();
		for (int i = a_manacalaBoard.getPlayer2StartIndex() ; i >= a_manacalaBoard.getPlayer2EndIndex() ; i--)
			w_newBuffer.append(String.valueOf(w_manacalaBoard[i])).append(" ");
		w_outputList.add(w_newBuffer.substring(0,w_newBuffer.length() - 1));
		
		w_newBuffer = new StringBuffer();
		for (int i = a_manacalaBoard.getPlayer1StartIndex() ; i <= a_manacalaBoard.getPlayer1EndIndex() ; i++)
			w_newBuffer.append(String.valueOf(w_manacalaBoard[i])).append(" ");
		w_outputList.add(w_newBuffer.substring(0,w_newBuffer.length() - 1));
		
		w_outputList.add(String.valueOf(w_manacalaBoard[a_manacalaBoard.getPlayer2MancalaIndex()]));
		w_outputList.add(String.valueOf(w_manacalaBoard[a_manacalaBoard.getPlayer1MancalaIndex()]));
		
		return w_outputList;
	}
	
	public static void playManacalaGreedy(Node a_manacalaBoard) throws Exception
	{
		playByGreedyMethod(a_manacalaBoard);
		printMancalaState(getStateOfMancalaBoard(a_manacalaBoard));
	}
	
	public static boolean distributeManacala(Node a_manacalaBoard, int a_position) throws Exception
	{
		int[] w_manacalaboard = a_manacalaBoard.getMancalaBoard();
		int count = w_manacalaboard[a_position];
		w_manacalaboard[a_position] = 0;
		a_position++;
		
		
		int startIndex = a_manacalaBoard.getPlayer() == 1 ? a_manacalaBoard.getPlayer1StartIndex() : a_manacalaBoard.getPlayer2EndIndex();
		int endIndex = a_manacalaBoard.getPlayer() == 1 ? a_manacalaBoard.getPlayer1EndIndex() : a_manacalaBoard.getPlayer2StartIndex();
		
		boolean w_specialCase = false;
		
		while(count != 0)
		{
			if(a_manacalaBoard.getPlayer() == 1 && a_position==a_manacalaBoard.getPlayer2MancalaIndex())
				a_position = 0;
			else if(a_manacalaBoard.getPlayer() == 2 && a_position==a_manacalaBoard.getPlayer1MancalaIndex())
				a_position++;
			
			if (w_manacalaboard[a_position] == 0 && (a_position >= startIndex && a_position <= endIndex) && count == 1)
				w_specialCase = true;
			
			w_manacalaboard[a_position] = w_manacalaboard[a_position] + 1;
			if (a_manacalaBoard.getMancalaBoard().length - 1 == a_position)
				a_position = 0;
			else
				a_position++;
			count--;
		}
		a_position--;
		if (a_position < 0)
			a_position = a_position + w_manacalaboard.length;
		if (w_specialCase)
		{
			int w_index = a_manacalaBoard.getPlayer() == 1 ? a_manacalaBoard.getPlayer1MancalaIndex() : a_manacalaBoard.getPlayer2MancalaIndex();
			int w_oppositeIndex = (a_position) + 2 * (a_manacalaBoard.getManacalaHoles() - (a_position));
			int w_sumCoins = w_manacalaboard[a_position] + w_manacalaboard[w_oppositeIndex];
			w_manacalaboard[w_index] += w_sumCoins;
			w_manacalaboard[a_position] = 0;
			w_manacalaboard[w_oppositeIndex] = 0;
		}
		
		boolean w_isGameEndCondition = true;
		int i = startIndex;
		while (i <= endIndex)
		{
			if (w_manacalaboard[i] > 0)
				w_isGameEndCondition = false;
			i++;
		}
		/*Player 1 Game End*/
		int w_sumOfCoins = 0;
		int opponentStartIndex = a_manacalaBoard.getPlayer() == 1 ? a_manacalaBoard.getPlayer2EndIndex() : a_manacalaBoard.getPlayer1StartIndex();
		int opponentEndIndex = a_manacalaBoard.getPlayer() == 1 ? a_manacalaBoard.getPlayer2StartIndex() : a_manacalaBoard.getPlayer1EndIndex();
		if (w_isGameEndCondition)
		{
			i = opponentStartIndex;
			while (i <= opponentEndIndex)
			{
				w_sumOfCoins += w_manacalaboard[i];
				w_manacalaboard[i] = 0;
				i++;
			}
			a_manacalaBoard.setIsBoardEmpty(true);
			w_manacalaboard[a_manacalaBoard.getPlayer() == 1 ? a_manacalaBoard.getPlayer2MancalaIndex() : a_manacalaBoard.getPlayer1MancalaIndex()] += w_sumOfCoins;
		}
		
		w_isGameEndCondition = true;
		i = opponentStartIndex;
		while (i <= opponentEndIndex)
		{
			if (w_manacalaboard[i] > 0)
				w_isGameEndCondition = false;
			i++;
		}
		/*Player 2 Game End*/
		w_sumOfCoins = 0;
		int samePlayerStartIndex = a_manacalaBoard.getPlayer() == 2 ? a_manacalaBoard.getPlayer2EndIndex() : a_manacalaBoard.getPlayer1StartIndex();
		int samePlayerEndIndex = a_manacalaBoard.getPlayer() == 2 ? a_manacalaBoard.getPlayer2StartIndex() : a_manacalaBoard.getPlayer1EndIndex();
		
		if (w_isGameEndCondition)
		{
			i = samePlayerStartIndex;
			while (i <= samePlayerEndIndex)
			{
				w_sumOfCoins += w_manacalaboard[i];
				w_manacalaboard[i] = 0;
				i++;
			}
			a_manacalaBoard.setIsBoardEmpty(true);
			w_manacalaboard[a_manacalaBoard.getPlayer() == 1 ? a_manacalaBoard.getPlayer1MancalaIndex() : a_manacalaBoard.getPlayer2MancalaIndex()] += w_sumOfCoins;
		}
		
		a_manacalaBoard.setMancalaBoard(w_manacalaboard);
		
		if (a_position == (a_manacalaBoard.getPlayer() == 1 ? a_manacalaBoard.getPlayer1MancalaIndex() : a_manacalaBoard.getPlayer2MancalaIndex()))
			return true;
		
		return false;
	}
	
	public static void playByGreedyMethod(Node a_manacalaBoard) throws Exception
	{
		int startIndex = a_manacalaBoard.getPlayer() == 1 ? a_manacalaBoard.getPlayer1StartIndex() : a_manacalaBoard.getPlayer2StartIndex();
		int endIndex = a_manacalaBoard.getPlayer() == 1 ? a_manacalaBoard.getPlayer1EndIndex() : a_manacalaBoard.getPlayer2EndIndex();
		a_manacalaBoard.setValueFromChild(Integer.MIN_VALUE);
		int w_blockNo = 2;
		Node w_tempNode = a_manacalaBoard.toCopyNode();
		for (int i = startIndex; ((a_manacalaBoard.getPlayer() == 1) ? i <= endIndex : i >= endIndex) ;  )
		{
			Node w_childNode = w_tempNode.toCopyNode();
			
			w_childNode.setValueFromChild(Integer.MIN_VALUE);
			w_childNode.setNodeName(w_childNode.getNodeName() + (w_blockNo++));
			if (w_tempNode.getMancalaBoard()[i] > 0)
			{
				boolean w_isNextChannce = distributeManacala(w_childNode, i);
	
				if (w_isNextChannce && !w_childNode.isEmptyBoard())
					playByGreedyMethod(w_childNode);
				else
					w_childNode.setValueFromChild(w_childNode.getEvaluationFunctionValue());
				
				if (a_manacalaBoard.getValueFromChild() < w_childNode.getValueFromChild())
				{
					a_manacalaBoard.setMancalaBoard(w_childNode.getMancalaBoard());
					a_manacalaBoard.setValueFromChild(w_childNode.getValueFromChild());
				}
			}
			if (a_manacalaBoard.getPlayer() == 1)
				i++;
			else 
				i--;
		}
	}
	
	public static int maxValueFunction(Node a_parentNode, boolean a_isParentNextChance, boolean a_fromMaxFunction, 
												int a_depth, int a_player) throws Exception
	{
		int v = Integer.MIN_VALUE;
		a_parentNode.setValueFromChild(Integer.MIN_VALUE);
		
		if (a_parentNode.isEmptyBoard())
		{
			if (a_parentNode.getCutOffDepth() == a_depth)
			{
				if (a_isParentNextChance)
					a_parentNode.addInstructions(a_parentNode.getNodeName() + "," + a_depth + "," + a_parentNode.getValueString());
			}
			else
			{
				a_parentNode.addInstructions(a_parentNode.getNodeName() + "," + a_depth + "," + a_parentNode.getValueString());
			}
			a_parentNode.setValueFromChild(a_parentNode.getEvaluationFunctionValue());
			return a_parentNode.getEvaluationFunctionValue();
		}
		
		if (a_parentNode.getCutOffDepth() == a_depth && !a_fromMaxFunction)
		{
			a_parentNode.setValueFromChild(a_parentNode.getEvaluationFunctionValue());
			return a_parentNode.getEvaluationFunctionValue();
		}
		
		int startIndex = a_player == 1 ? a_parentNode.getPlayer1StartIndex() : a_parentNode.getPlayer2StartIndex();
		int endIndex = a_player == 1 ? a_parentNode.getPlayer1EndIndex() : a_parentNode.getPlayer2EndIndex();
		
		int w_blockNo = 2;
		for (int i = startIndex; ((a_player == 1) ? i <= endIndex : i >= endIndex) ;  )
		{
			Node w_childNode = a_parentNode.toCopyNode();
			String w_parentName = null;
			
			if (!a_isParentNextChance)
			{
				if ("root".equals(a_parentNode.getNodeName()))
					w_parentName = (a_player == 1 ? "B" : "A");
				else
					w_parentName = (a_parentNode.getNodeName().startsWith("B")) ? "A" : "B";
			}
			else
				w_parentName = (a_parentNode.getNodeName().startsWith("B")) ? "B" : "A";
			
			w_childNode.setNodeName(w_parentName + (w_blockNo++));
			
			if (w_childNode.getMancalaBoard()[i] > 0)
			{
				boolean w_isNextChannce = false;
				
				if (w_childNode.getMancalaBoard()[i] != 0)
				{
					int w_originalPlayer = w_childNode.getPlayer();
					w_childNode.setPlayer(a_player);
					w_isNextChannce = distributeManacala(w_childNode, i);
					w_childNode.setPlayer(w_originalPlayer);
				}
				else
				{
					if (a_player == 1)
						i++;
					else 
						i--;
					continue;
				}
				a_parentNode.addInstructions(a_parentNode.getNodeName() + "," + a_depth + "," + a_parentNode.getValueString());

				int w_depth = a_depth;
				if (!a_isParentNextChance)
					w_depth++;
				
				int w_tempValue = 0;

				if (w_isNextChannce)
					w_tempValue = maxValueFunction(w_childNode, w_isNextChannce, true, w_depth, a_player);
				else
					w_tempValue = minValueFunction(w_childNode, w_isNextChannce, false, w_depth, a_player == 1 ? 2 : 1);

					
				if (a_depth <= 1)
				{
					if (w_childNode.getValueFromChild() > a_parentNode.getValueFromChild())
					{
						/*
						 * if w_isNextChannce = false = > childNode is lowest node at level 0, 1
						 * else propagete Final board to parents final board
						 */
						if (!w_isNextChannce || w_childNode.isEmptyBoard())
							a_parentNode.setFinalBoard(w_childNode.getMancalaBoard());
						else
							a_parentNode.setFinalBoard(w_childNode.getFinalBoard());
					}
				}
				v = Integer.max(v, w_tempValue);
				if (v > a_parentNode.getValueFromChild())
				{
					a_parentNode.setValueFromChild(v);
				}
				a_parentNode.addInstructions(w_childNode.getNodeName() + "," + w_depth + "," + w_childNode.getValueString());
			}
				
			if (a_player == 1)
				i++;
			else 
				i--;
		}
		if ("root".equals(a_parentNode.getNodeName()) && a_depth == 0)
		{
			a_parentNode.addInstructions(a_parentNode.getNodeName()+ "," + a_depth + "," + a_parentNode.getValueString());
		}
		return v;
	}
	
	public static int minValueFunction(Node a_parentNode, boolean a_isParentNextChance, boolean a_fromMinFunction, 
			int a_depth, int a_player) throws Exception
	{
		int v = Integer.MAX_VALUE;
		a_parentNode.setValueFromChild(Integer.MAX_VALUE);
		
		if (a_parentNode.isEmptyBoard())
		{
			if (a_parentNode.getCutOffDepth() == a_depth)
			{
				if (a_isParentNextChance)
					a_parentNode.addInstructions(a_parentNode.getNodeName() + "," + a_depth + "," + a_parentNode.getValueString());
			}
			else
			{
				a_parentNode.addInstructions(a_parentNode.getNodeName() + "," + a_depth + "," + a_parentNode.getValueString());
			}
			a_parentNode.setValueFromChild(a_parentNode.getEvaluationFunctionValue());
			return a_parentNode.getEvaluationFunctionValue();
		}
		
		if (a_parentNode.getCutOffDepth() == a_depth && !a_fromMinFunction)
		{
			a_parentNode.setValueFromChild(a_parentNode.getEvaluationFunctionValue());
			return a_parentNode.getEvaluationFunctionValue();
		}

		int startIndex = a_player == 1 ? a_parentNode.getPlayer1StartIndex() : a_parentNode.getPlayer2StartIndex();
		int endIndex = a_player == 1 ? a_parentNode.getPlayer1EndIndex() : a_parentNode.getPlayer2EndIndex();
		
		int w_blockNo = 2;
		for (int i = startIndex; ((a_player == 1) ? i <= endIndex : i >= endIndex) ;  )
		{
			Node w_childNode = a_parentNode.toCopyNode();
			String w_parentName = null;
			
			if (!a_isParentNextChance)
				w_parentName = (a_parentNode.getNodeName().startsWith("B")) ? "A" : "B";
			else
				w_parentName = (a_parentNode.getNodeName().startsWith("B")) ? "B" : "A";
			
			w_childNode.setNodeName(w_parentName + (w_blockNo++));
			
			if (w_childNode.getMancalaBoard()[i] > 0)
			{
				boolean w_isNextChannce = false;
				
				if (w_childNode.getMancalaBoard()[i] != 0)
				{
					int w_originalPlayer = w_childNode.getPlayer();
					w_childNode.setPlayer(a_player);
					w_isNextChannce = distributeManacala(w_childNode, i);
					w_childNode.setPlayer(w_originalPlayer);
				}
				else
				{
					if (a_player == 1)
						i++;
					else 
						i--;
					continue;
				}
				a_parentNode.addInstructions(a_parentNode.getNodeName() + "," + a_depth + "," + a_parentNode.getValueString());

				int w_depth = a_depth;
				if (!a_isParentNextChance)
					w_depth++;
				
				int w_tempValue = 0;
		
				if (w_isNextChannce)
					w_tempValue = minValueFunction(w_childNode, w_isNextChannce, true, w_depth, a_player);
				else
					w_tempValue = maxValueFunction(w_childNode, w_isNextChannce, false, w_depth, a_player == 1 ? 2 : 1);
								
				v = Integer.min(v, w_tempValue);
				
				if (v < a_parentNode.getValueFromChild())
				{
					a_parentNode.setValueFromChild(v);
				}
				a_parentNode.addInstructions(w_childNode.getNodeName() + "," + w_depth + "," + w_childNode.getValueString());
			}
				
			if (a_player == 1)
				i++;
			else 
				i--;
		}
		return v;
	}
	
	public static void playByAlphaBetaMethod(Node a_manacalaBoard) throws Exception
	{
		a_manacalaBoard.setNodeName("root");
		a_manacalaBoard.setValueFromChild(Integer.MIN_VALUE);
		
		a_manacalaBoard.addInstructions("Node,Depth,Value,Alpha,Beta");
		Integer w_alpha = Integer.MIN_VALUE;
		Integer w_beta = Integer.MAX_VALUE;
		a_manacalaBoard.setAlphaValue(w_alpha);
		a_manacalaBoard.setBetaValue(w_beta);
		maxValueAlphaBetaFunction(a_manacalaBoard, false, false, 0, a_manacalaBoard.getPlayer());
		
		a_manacalaBoard.setMancalaBoard(a_manacalaBoard.getFinalBoard());
		printMancalaState(getStateOfMancalaBoard(a_manacalaBoard));
		printTraverseLog(a_manacalaBoard.getInstructions());
	}
	
	public static int maxValueAlphaBetaFunction(Node a_parentNode, boolean a_isParentNextChance, boolean a_fromMaxFunction, 
			int a_depth, int a_player) throws Exception	
	{
		int v = Integer.MIN_VALUE;
		a_parentNode.setValueFromChild(Integer.MIN_VALUE);
		
		if (a_parentNode.isEmptyBoard())
		{
			if (a_parentNode.getCutOffDepth() == a_depth)
			{
				if (a_isParentNextChance)
					a_parentNode.addInstructions(a_parentNode.getNodeName() + "," + a_depth + "," + a_parentNode.getValueString()
							+ "," + a_parentNode.getAlphaValueString() + "," + a_parentNode.getBetaValueString());
			}
			else
			{
				a_parentNode.addInstructions(a_parentNode.getNodeName() + "," + a_depth + "," + a_parentNode.getValueString()
						+ "," + a_parentNode.getAlphaValueString() + "," + a_parentNode.getBetaValueString());
			}
			a_parentNode.setValueFromChild(a_parentNode.getEvaluationFunctionValue());
			return a_parentNode.getEvaluationFunctionValue();
		}
		
		if (a_parentNode.getCutOffDepth() == a_depth && !a_fromMaxFunction)
		{
			a_parentNode.setValueFromChild(a_parentNode.getEvaluationFunctionValue());
			return a_parentNode.getEvaluationFunctionValue();
		}

		int startIndex = a_player == 1 ? a_parentNode.getPlayer1StartIndex() : a_parentNode.getPlayer2StartIndex();
		int endIndex = a_player == 1 ? a_parentNode.getPlayer1EndIndex() : a_parentNode.getPlayer2EndIndex();
		
		int w_blockNo = 2;
		for (int i = startIndex; ((a_player == 1) ? i <= endIndex : i >= endIndex) ;  )
		{
			Node w_childNode = a_parentNode.toCopyNode();
			String w_parentName = null;
			
			if (!a_isParentNextChance)
			{
				if ("root".equals(a_parentNode.getNodeName()))
					w_parentName = (a_player == 1 ? "B" : "A");
				else
					w_parentName = (a_parentNode.getNodeName().startsWith("B")) ? "A" : "B";
			}
			else
				w_parentName = (a_parentNode.getNodeName().startsWith("B")) ? "B" : "A";
			
			w_childNode.setNodeName(w_parentName + (w_blockNo++));
			
			if (w_childNode.getMancalaBoard()[i] > 0)
			{
				boolean w_isNextChannce = false;
				
				if (w_childNode.getMancalaBoard()[i] != 0)
				{
					int w_originalPlayer = w_childNode.getPlayer();
					w_childNode.setPlayer(a_player);
					w_isNextChannce = distributeManacala(w_childNode, i);
					w_childNode.setPlayer(w_originalPlayer);
				}
				else
				{
					if (a_player == 1)
						i++;
					else 
						i--;
					continue;
				}
				a_parentNode.addInstructions(a_parentNode.getNodeName() + "," + a_depth + "," + a_parentNode.getValueString()
												+ "," + a_parentNode.getAlphaValueString() + "," + a_parentNode.getBetaValueString());
				
				int w_depth = a_depth;
				if (!a_isParentNextChance)
					w_depth++;
				
				int w_tempValue = 0;

				if (w_isNextChannce)
					w_tempValue = maxValueAlphaBetaFunction(w_childNode, w_isNextChannce, true, w_depth, a_player);
				else
					w_tempValue = minValueAlphaBetaFunction(w_childNode, w_isNextChannce, false, w_depth, a_player == 1 ? 2 : 1);

				if (a_depth <= 1)
				{
					if (w_childNode.getValueFromChild() > a_parentNode.getValueFromChild())
					{
						/*
						 * if w_isNextChannce = false = > childNode is lowest node at level 0, 1
						 * else propagete Final board to parents final board
						 */
						if (!w_isNextChannce || w_childNode.isEmptyBoard())
							a_parentNode.setFinalBoard(w_childNode.getMancalaBoard());
						else
							a_parentNode.setFinalBoard(w_childNode.getFinalBoard());
					}
				}
				
				if (w_childNode.getValueFromChild() > a_parentNode.getValueFromChild())
				{
					a_parentNode.setValueFromChild(w_childNode.getValueFromChild());
				}
				
				if (v < w_tempValue)
					v = w_tempValue;
				
				a_parentNode.addInstructions(w_childNode.getNodeName() + "," + w_depth + "," + w_childNode.getValueString()
						+ "," + w_childNode.getAlphaValueString() + "," + w_childNode.getBetaValueString());

				
				if (v >= a_parentNode.getBetaValue())
				{
					return v;
				}
				
				if (v > a_parentNode.getAlphaValue())
				{
					a_parentNode.setAlphaValue(v);
				}

			}
				
			if (a_player == 1)
				i++;
			else 
				i--;
		}
		if ("root".equals(a_parentNode.getNodeName()) && a_depth == 0)
		{
			a_parentNode.addInstructions(a_parentNode.getNodeName()+ "," + a_depth + "," + a_parentNode.getValueString()
																	+ "," + a_parentNode.getAlphaValueString() + "," + a_parentNode.getBetaValueString());
		}
		return v;
	}
	public static int minValueAlphaBetaFunction(Node a_parentNode, boolean a_isParentNextChance, boolean a_fromMinFunction, 
			int a_depth, int a_player) throws Exception
	{
		int v = Integer.MAX_VALUE;
		a_parentNode.setValueFromChild(Integer.MAX_VALUE);
		
		if (a_parentNode.isEmptyBoard())
		{
			if (a_parentNode.getCutOffDepth() == a_depth)
			{
				if (a_isParentNextChance)
					a_parentNode.addInstructions(a_parentNode.getNodeName() + "," + a_depth + "," + a_parentNode.getValueString()
							+ "," + a_parentNode.getAlphaValueString() + "," + a_parentNode.getBetaValueString());
			}
			else
			{
				a_parentNode.addInstructions(a_parentNode.getNodeName() + "," + a_depth + "," + a_parentNode.getValueString()
						+ "," + a_parentNode.getAlphaValueString() + "," + a_parentNode.getBetaValueString());
			}
			a_parentNode.setValueFromChild(a_parentNode.getEvaluationFunctionValue());
			return a_parentNode.getEvaluationFunctionValue();
		}
		
		if (a_parentNode.getCutOffDepth() == a_depth && !a_fromMinFunction)
		{
			a_parentNode.setValueFromChild(a_parentNode.getEvaluationFunctionValue());
			return a_parentNode.getEvaluationFunctionValue();
		}

		
		int startIndex = a_player == 1 ? a_parentNode.getPlayer1StartIndex() : a_parentNode.getPlayer2StartIndex();
		int endIndex = a_player == 1 ? a_parentNode.getPlayer1EndIndex() : a_parentNode.getPlayer2EndIndex();
		
		int w_blockNo = 2;
		for (int i = startIndex; ((a_player == 1) ? i <= endIndex : i >= endIndex) ;  )
		{
			Node w_childNode = a_parentNode.toCopyNode();
			String w_parentName = null;
			
			if (!a_isParentNextChance)
				w_parentName = (a_parentNode.getNodeName().startsWith("B")) ? "A" : "B";
			else
				w_parentName = (a_parentNode.getNodeName().startsWith("B")) ? "B" : "A";
			
			w_childNode.setNodeName(w_parentName + (w_blockNo++));
			
			if (w_childNode.getMancalaBoard()[i] > 0)
			{
				boolean w_isNextChannce = false;
				
				if (w_childNode.getMancalaBoard()[i] != 0)
				{
					int w_originalPlayer = w_childNode.getPlayer();
					w_childNode.setPlayer(a_player);
					w_isNextChannce = distributeManacala(w_childNode, i);
					w_childNode.setPlayer(w_originalPlayer);
				}
				else
				{
					if (a_player == 1)
						i++;
					else 
						i--;
					continue;
				}
				a_parentNode.addInstructions(a_parentNode.getNodeName() + "," + a_depth + "," + a_parentNode.getValueString()
												+ "," + a_parentNode.getAlphaValueString() + "," + a_parentNode.getBetaValueString());

				int w_depth = a_depth;
				if (!a_isParentNextChance)
					w_depth++;
				
				int w_tempValue = 0;

				if (w_isNextChannce)
					w_tempValue = minValueAlphaBetaFunction(w_childNode, w_isNextChannce, true, w_depth, a_player);
				else
					w_tempValue = maxValueAlphaBetaFunction(w_childNode, w_isNextChannce, false, w_depth, a_player == 1 ? 2 : 1);
								
				if (w_childNode.getValueFromChild() < a_parentNode.getValueFromChild())
				{
					a_parentNode.setValueFromChild(w_childNode.getValueFromChild());
				}
				
				if (v > w_tempValue)
					v = w_tempValue;
				
				a_parentNode.addInstructions(w_childNode.getNodeName() + "," + w_depth + "," + w_childNode.getValueString()
						+ "," + w_childNode.getAlphaValueString() + "," + w_childNode.getBetaValueString());

				
				if (a_parentNode.getAlphaValue() >= v)
				{
					return v;
				}
				
				if (v < a_parentNode.getBetaValue())
				{
					a_parentNode.setBetaValue(v);
				}
			}
				
			if (a_player == 1)
				i++;
			else 
				i--;
		}
		return v;
	}
	
	public static String getStringValue(Integer a_value) throws Exception
	{
		if (a_value == Integer.MAX_VALUE)
			return "Infinity";
		else if (a_value == Integer.MIN_VALUE)
				return "-Infinity";
			else
				return String.valueOf(a_value);
	}
}