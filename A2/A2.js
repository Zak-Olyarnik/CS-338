/*
Zakary Olyarnik
zwo24@drexel.edu
CS 338:GUI, Assignment 2
*/

function drawLine()
{
	// This function should draw the line based on user input

	// first get the canvas and context
	var canvas = document.getElementById("myCanvas");
	var context = canvas.getContext("2d");

	// set the start and end coordinates based on user input 
	var x_start = document.getElementById("x_start").value;
	var x_end = document.getElementById("x_end").value;
	var y_start = document.getElementById("y_start").value;
	var y_end = document.getElementById("y_end").value;

	// set the line color based on user input
	var colors = document.getElementById("colors");
    context.strokeStyle = colors.options[colors.selectedIndex].value;

	// draw the line from start coordinate to end coordinates with
		// correct line color
    context.beginPath();	// needed so clearing works correctly
    context.moveTo(x_start, y_start);
    context.lineTo(x_end, y_end);
    context.stroke();
}

function clearCanvas()
{
	// clear all the contents of the canvas
	
	// first get the canvas and the context
	var canvas = document.getElementById("myCanvas");
	var context = canvas.getContext("2d");
	
	// use clearRect with canvas.width and canvas.height
	// to clear entire canvas 
	context.clearRect(0, 0, canvas.width, canvas.height);
}

