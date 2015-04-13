/*
Test response options
 */
CREATE TABLE Answers (id INT , answer_text VARCHAR(100), related_questions_id INT, PRIMARY KEY (id),
  FOREIGN KEY (related_questions_id) REFERENCES Questions(id));

/*
Table lesson contains meta information about lesson.
covered_topics - topics covered in lesson with with their complexity. if we have n topics in lesson covered_topics contain n numbers separated by ;
name - lesson name
complexity - lesson total complexity
alternative_material_ids contains ids of alternative materials separated by ; if exists
lesson - some reference to the lessons content
lessons_mlb - lessons ids that must be learned before that lessons
 */
CREATE TABLE Lessons (id INT ,covered_topics VARCHAR (500),name VARCHAR (30),comlexity DOUBLE , lessons_mlb VARCHAR(500),
  lesson INT, alternative_material_ids INT, PRIMARY KEY (id));

/*
Table Questions contains question info
id - id
question_text - question in text form
related_lesson_id - lesson id (in Table Lessons) that have that question in test after lesson
correct_answer_id - id of correct answer (in Table Answers)
complexity - question complexity
 */
CREATE TABLE Questions (id INT , question_text VARCHAR(100), related_lesson_id INT, correct_answer_id INT,comlexity DOUBLE ,
  PRIMARY KEY (id), FOREIGN KEY (related_lesson_id) REFERENCES Lessons(id), FOREIGN KEY (correct_answer_id) REFERENCES Answers(id));

/*
Table results stores students test results
 student_id - student id
 lesson_id - lesson id (test id)
 res - results in form  - question id:correct or not:question complexity
 */
CREATE TABLE Results (id INT , student_id INT,lesson_id INT ,res VARCHAR (500), PRIMARY KEY (id),
  FOREIGN KEY (student_id) REFERENCES Students(id),FOREIGN  KEY (lesson_id) REFERENCES Lessons(id));

/*
Table students contains information about students
first_name - first name
last_name - last name
login - student login
pass_hash - password hash
current_lesson_id - current lesson id
knowledge_vector - vector of knowledge, consist of n components (n - number of topics) where every component is student knowledge in i-th topics
educability - educability. recently unneeded
lessons_passed - lessons that have been learned
 */
CREATE TABLE Students (id INT , first_name VARCHAR(30), last_name VARCHAR(30), login VARCHAR(30), pass_hash VARCHAR(20),
  current_lesson_id INT, knowledge_vector VARCHAR (500),educability DOUBLE,lessons_passed VARCHAR (500), PRIMARY KEY (id),
  FOREIGN KEY (current_lesson_id)  REFERENCES Lessons(id));