Zakary Olyarnik

zwo24@drexel.edu

CS338:GUI, MyGradebook

Project 


Overview:

	MyGradebook is an application students can use to track their grades in 
order to ultimately answer the question
"What do I need to get on a specific
 assingment in order to score a certain average in the course?"  This is a
very common
question for students to have, but unfortunately the amount of 
work required to track and tabulate grades in order to
perform this 
calculation is more than most would want to do.  The main goal of 
MyGradebook, therefore, is to simplify the
grade entry process as much as 
possible, to encourage students to continue using it.  The second goal of
 the app is to be
able to see and calculate grades at any point in the
 quarter, so class averages are updated with every new grade entry.

Predictive calculations can also be run at any time, but obviously the more
 data available, the better.



Instructions to Run:

	I have provided a makefile which compiles and runs MyGradebook with a set of 
sample data so that calculations and
other functionality can be tested 
immediately.  Simply unzip and type "make".  Note that when testing on tux,
the necessary
tunneling made all of my JDialogs extremely slow to load.  I
 have tested the code locally in Eclipse, and this problem is
solved there 
but my chosen font and images no longer render correctly.  It's sort of
 lose-lose, then, but I believe the
latter problem is more to do with my 
project not being organized the way Eclipse prefers.  If everything is still
 being graded
on tux, the only problem is the slow JDialog response time.




Comments:

-I worked very hard to make sure everything displayed correctly in the UI.
  To that end, I was forced to restrict the minimum
window size, and in fact 
the sizes of all the components.  I found that just setting the minimum size
 of other components like
labels and buttons never worked unless all three 
minimum, maximum, and preferred sizes were set.  But doing so fixes the

absolute size of the component.  Research revealed that many Layout Managers 
do not respect these attributes, or only some of
them, but even then 
combinations that the documentation said should have worked didn't always.
  The end result is that while the
window can be resized larger, all 
components will remain the same size (although they will move to remain 
centered)

-Each JDialog is created as a new class in the file of the class
 representing the JPanel it launched from.  Much of the code is
similar 
between these JDialogs, but enough is different that trying to abstract it
 and pass in arguments became too complicated
too quickly.

-In general, my code structure is to create each different screen as a Card
Layout panel, with a refresh method that is called 
right before the Layout 
flips.  This way unique class data can be displayed even after initializing
 the panel when no data is yet
known.

-I was able to fake an accordion layout by creating each row of data in a 
separate panel and adding or subtracting those panels as
necessary.  The 
accordion implementations I came across didn't offer the right spacing I was
 looking for, and trying to use a table
also failed quickly.  I am happy with 
how it ended up.

-The math equations themselves were extremely challenging to derive.  I 
normally use Excel spreadsheets to calculate my predictive
grades, so I
 generally knew what to do, but the tricky part was being able to still 
calculate when the available data is less than
complete.  For a robust 
program, I also needed to anticipate things like the known weights not 
totalling 100 and therefore needing
to extrapolate.  I tested my formulas
  with a lot of cases of sample data and they all return realistic results, 
although these
results may not seem realistic in and of themselves.  For
 instance, if MyGradebook returns that you need a 400% on a grade to 
achieve
 your desired class average, it is still probably correct due to the other
 known data, weighting, etc.  Again, I tested
several cases by plugging in 
MyGradebook's result as a new "known" grade and watching the calculated 
average turn to what I said I
wanted, so all the math checks out.

