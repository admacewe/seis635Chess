<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
	<title>Play Chess (same computer) - Brasee.com</title>
	<link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="css/menu.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="css/chessSingleClient.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="css/smoothness/jquery-ui-1.7.1.custom.css" type="text/css" media="screen" />
	<script type="text/javascript" src="js/chessSingleClient.js"></script>
	<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.7.1.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery.corner.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			initialize();
		});
	</script>
</head>
<body>
	<div id="pageborder">
		<div id="page">
			<div id="header"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td align="left" valign="top"><a href="<c:url value="/"/>"><img src="img/games_header_link.jpg" alt="Brasee.com Games"/></a></td><td align="right" valign="top"><a href="/"><img src="img/games_header_back_to_main.jpg" alt="Brasee.com Home"/></a></td></tr></table></div>
			<div id="nav">
				<ul>
					<li><a href="lobby.htm"><img src="img/chess_icon.png" alt="Chess Lobby"/> Chess Lobby</a></li>
					<li><a href="chessSingleClient.htm"><img src="img/chess_icon.png" alt="Play Chess (same browser)"/> Play Chess (same browser)</a></li>					
				</ul>
  			</div> 
			<div id="container">
				<div id="leftcontent">

					<table border="0" cellpadding="0" cellspacing="0" align="center">
						<tr>
							<td><img src="img/king_white_small.png"/>&nbsp;</td>
							<td><b><span id="white_name">White</span></b></td>
							<td><b>&nbsp;vs.&nbsp;</b></td>
							<td><b><span id="black_name">Black</span></b></td>
							<td>&nbsp;<img src="img/king_black_small.png"/></td>
						</tr>
						<tr>
							<td colspan="5">
								<div id="playersTurnDiv"><span id="playersTurn"></span></div>
							</td>
						</tr>
					</table>
					
					<div class="subpanelDivOuter"><div class="subpanelDivInner">
						<div class="subpanelHeader">Captured Pieces</div>
						<div id="capturedPiecesWhite" class="capturedPiecesDiv"></div>
						<div id="capturedPiecesBlack" class="capturedPiecesDiv"></div>
					</div></div>
					
					<div class="subpanelDivOuter"><div class="subpanelDivInner">
						<div class="subpanelHeader">Moves</div>
						<div id="movesTableDiv">
							<table id="movesTable" border="0" cellpadding="0" cellspacing="0" align="center">
								<tr>
									<td class="movesTableHeader1"></td>
									<td class="movesTableHeader2"><img src="img/king_white_small.png"/></td>
									<td class="movesTableHeader3"><img src="img/king_black_small.png"/></td>
								</tr>
							</table>
						</div>
					</div></div>

				</div> <!-- /leftcontent -->
				
				<div id="centercontent">
					<div id="board">
						<table id="chessboard" cellspacing="0" cellpadding="0">
							<tr>
								<td><img class="square" id="a8" src="img/blank.gif"/></td>
								<td><img class="square" id="b8" src="img/blank.gif"/></td>
								<td><img class="square" id="c8" src="img/blank.gif"/></td>
								<td><img class="square" id="d8" src="img/blank.gif"/></td>
								<td><img class="square" id="e8" src="img/blank.gif"/></td>
								<td><img class="square" id="f8" src="img/blank.gif"/></td>
								<td><img class="square" id="g8" src="img/blank.gif"/></td>
								<td><img class="square" id="h8" src="img/blank.gif"/></td>
							</tr>
							<tr>
								<td><img class="square" id="a7" src="img/blank.gif"/></td>
								<td><img class="square" id="b7" src="img/blank.gif"/></td>
								<td><img class="square" id="c7" src="img/blank.gif"/></td>
								<td><img class="square" id="d7" src="img/blank.gif"/></td>
								<td><img class="square" id="e7" src="img/blank.gif"/></td>
								<td><img class="square" id="f7" src="img/blank.gif"/></td>
								<td><img class="square" id="g7" src="img/blank.gif"/></td>
								<td><img class="square" id="h7" src="img/blank.gif"/></td>
							</tr>
							<tr>
								<td><img class="square" id="a6" src="img/blank.gif"/></td>
								<td><img class="square" id="b6" src="img/blank.gif"/></td>
								<td><img class="square" id="c6" src="img/blank.gif"/></td>
								<td><img class="square" id="d6" src="img/blank.gif"/></td>
								<td><img class="square" id="e6" src="img/blank.gif"/></td>
								<td><img class="square" id="f6" src="img/blank.gif"/></td>
								<td><img class="square" id="g6" src="img/blank.gif"/></td>
								<td><img class="square" id="h6" src="img/blank.gif"/></td>
							</tr>
							<tr>
								<td><img class="square" id="a5" src="img/blank.gif"/></td>
								<td><img class="square" id="b5" src="img/blank.gif"/></td>
								<td><img class="square" id="c5" src="img/blank.gif"/></td>
								<td><img class="square" id="d5" src="img/blank.gif"/></td>
								<td><img class="square" id="e5" src="img/blank.gif"/></td>
								<td><img class="square" id="f5" src="img/blank.gif"/></td>
								<td><img class="square" id="g5" src="img/blank.gif"/></td>
								<td><img class="square" id="h5" src="img/blank.gif"/></td>
							</tr>
							<tr>
								<td><img class="square" id="a4" src="img/blank.gif"/></td>
								<td><img class="square" id="b4" src="img/blank.gif"/></td>
								<td><img class="square" id="c4" src="img/blank.gif"/></td>
								<td><img class="square" id="d4" src="img/blank.gif"/></td>
								<td><img class="square" id="e4" src="img/blank.gif"/></td>
								<td><img class="square" id="f4" src="img/blank.gif"/></td>
								<td><img class="square" id="g4" src="img/blank.gif"/></td>
								<td><img class="square" id="h4" src="img/blank.gif"/></td>
							</tr>
							<tr>
								<td><img class="square" id="a3" src="img/blank.gif"/></td>
								<td><img class="square" id="b3" src="img/blank.gif"/></td>
								<td><img class="square" id="c3" src="img/blank.gif"/></td>
								<td><img class="square" id="d3" src="img/blank.gif"/></td>
								<td><img class="square" id="e3" src="img/blank.gif"/></td>
								<td><img class="square" id="f3" src="img/blank.gif"/></td>
								<td><img class="square" id="g3" src="img/blank.gif"/></td>
								<td><img class="square" id="h3" src="img/blank.gif"/></td>
							</tr>
							<tr>
								<td><img class="square" id="a2" src="img/blank.gif"/></td>
								<td><img class="square" id="b2" src="img/blank.gif"/></td>
								<td><img class="square" id="c2" src="img/blank.gif"/></td>
								<td><img class="square" id="d2" src="img/blank.gif"/></td>
								<td><img class="square" id="e2" src="img/blank.gif"/></td>
								<td><img class="square" id="f2" src="img/blank.gif"/></td>
								<td><img class="square" id="g2" src="img/blank.gif"/></td>
								<td><img class="square" id="h2" src="img/blank.gif"/></td>
							</tr>
							<tr>
								<td><img class="square" id="a1" src="img/blank.gif"/></td>
								<td><img class="square" id="b1" src="img/blank.gif"/></td>
								<td><img class="square" id="c1" src="img/blank.gif"/></td>
								<td><img class="square" id="d1" src="img/blank.gif"/></td>
								<td><img class="square" id="e1" src="img/blank.gif"/></td>
								<td><img class="square" id="f1" src="img/blank.gif"/></td>
								<td><img class="square" id="g1" src="img/blank.gif"/></td>
								<td><img class="square" id="h1" src="img/blank.gif"/></td>
							</tr>
						</table>
					</div> <!-- /board -->
				</div> <!-- /centercontent -->
				
				<div id="rightcontent">
					<c:if test="${!empty user}">
						<center><h3>Welcome, ${user.name}!</h3></center>
						<br/>
					</c:if>
					<div class="subpanelDivOuter"><div class="subpanelDivInner">
						<div class="subpanelHeader">Game Information</div>
						Human vs. Human
						<br/>
						Single client (same browser)
					</div></div>
							
					<div class="rightSubpanelDivOuter"><div class="rightSubpanelDivInner">
						<div class="subpanelHeader">Game Options</div>
						<br/>
						<a href="#" onclick="openResetDialog()">
							<div class="dialogButton ui-state-default ui-corner-all">
								<span class="dialogButtonText">Reset Game</span>
							</div>
						</a>
						<br/>
					</div></div> 
				</div> <!-- /rightcontent -->
				
			</div> <!-- /container -->
			<div id="footer-wrapper">
				<div id="footer">
					<br/>
					Created by Kaleb Brasee.
					<br/>
					<br/>
					<a href="http://code.google.com/p/java-chess-web">Available on Google Code</a>.
					<br/>
					&nbsp;
				</div> <!-- /footer -->
			</div> <!-- /footer-wrapper -->
		</div> <!-- /page -->
	</div> <!-- /pageborder -->
	
	<div id="promotion_dialog" title="Choose a piece for promotion:" style="display: none;">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td align="center"><img id="promotion_queen" src="img/queen_white.png" onclick="sendPromotion('queen')" class="promotionOption" /></td>
				<td align="center"><img id="promotion_knight" src="img/knight_white.png" onclick="sendPromotion('knight')" class="promotionOption" /></td>
				<td align="center"><img id="promotion_rook" src="img/rook_white.png" onclick="sendPromotion('rook')" class="promotionOption" /></td>
				<td align="center"><img id="promotion_bishop" src="img/bishop_white.png" onclick="sendPromotion('bishop')" class="promotionOption" /></td>
			</tr>
		</table>
	</div> <!-- /promotion_dialog -->
	
	<div id="reset_dialog" title="Are you sure you want to reset?" style="display: none;">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td align="center">
					<a href="#" onclick="closeResetDialogAndResetGame()"><div class="dialogButton ui-state-default ui-corner-all"><span class="dialogButtonText">Yes</span></div></a>
				</td>
				<td align="center">
					<a href="#" onclick="closeResetDialog()"><div class="dialogButton ui-state-default ui-corner-all"><span class="dialogButtonText">No</span></div></a>		
				</td>
			</tr>
		</table>
	</div> <!-- /reset_dialog -->
</body>
</html>