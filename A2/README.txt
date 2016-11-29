Zakary Olyarnik
zwo24@drexel.edu
CS 338:GUI, Assignment 2


In addition to adding the required functionality, I made the following changes to the provided files:
-Commented headers of all three files were changed to match what is outlined in the assignment pdf
-Spacing/indentation of all three files changed to match the guidelines laid out on codeacademy
-Clear button moved to be next to the Draw button instead of below the canvas
-Both buttons have the same hover behavior for consistency's sake, even though the assignment only
	specifies for the Draw button to have it.  The process for separating them (to prove that I
	could have if I'd wanted to) involves adding element ID's for each button and then specifying
	only the one we wanted to change in the CSS.

Comments:
-My chosen header font does not display in Firefox or Chrome, in a rare instance of IE doing something
	better.  I specified multiple fonts to get something elsse in its stead.
-I figured that there must be an easy way to alternate row colors, so I just looked up how and found a
	solution on w3schools.
-I tried experimenting with the * selector to condense some of the repetitive code (color selecting
	especially), but it seemed to behave inconsistently when trying to overwrite the values in more
	specific elements below.  I'm pretty sure I understand how it /should/ work, but that doesn't
	explain why I could successfully overwrite the table's font color but not its background color
	using the same code.
-Drawing lines seems to average the selected color and the canvas' background color.  Using a black line
	comes out gray, and other colors seem similarly muted.  Mashing the Draw button a few times will
	redraw the same line in the same place, and with each the color becomes more pure.  This theory
	is supported by the fact that redrawing over the same line with a different color /definitely/
	averages the two colors.  I would have left the canvas white to avoid this, but white is one of
	the line colors which needs to be supported.
-I really dislike coding in JavaScript but the functionality needed for this assignment was not bad.

Self-rating:
-Clean, logical visual layout: 2
-Part 1 Page Structure (HTML): 2
-Part 1 Page Style (CSS): 2
-Part 2: Page Structure (HTML): 2
-Part 2: Page CSS: 2
-Adding lines: 2
-Clearing canvas: 2
-Proper graphics drawing: 2

Collaboration Statement: I worked alone on this assignment.