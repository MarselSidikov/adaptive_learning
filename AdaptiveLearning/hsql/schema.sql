CREATE TABLE Answers (id INT , answer VARCHAR(100), rel_questions_id INT,block INT , PRIMARY KEY (id), FOREIGN KEY (rel_questions_id) REFERENCES Questions(id));
CREATE TABLE TestResult (id INT , lesson_id INT ,student_id INT ,mark DOUBLE ,PRIMARY KEY (id), FOREIGN KEY (student_id) REFERENCES Student(id),FOREIGN KEY (lesson_id) REFERENCES Lesson(id));
CREATE TABLE Test (id INT, question_ids VARCHAR (1000), PRIMARY KEY (id));
CREATE TABLE StudentAnswers (id INT , question_id INT , answer_id INT, is_correct INT ,test_result_id INT,block INT  ,PRIMARY KEY (id), FOREIGN KEY (test_result_id) REFERENCES TestResult(id));
CREATE TABLE Students (id INT , first_name VARCHAR(30), last_name VARCHAR(30), login VARCHAR(30), pass_hash VARCHAR(30), current_lesson INT,userToken Varchar(100), current_urls VARCHAR(1000), PRIMARY KEY (id), FOREIGN KEY (current_lesson)  REFERENCES Lessons(id));
CREATE TABLE Questions (id INT , question VARCHAR(100), correctAnswerId INT, block INT, PRIMARY KEY (id));
CREATE TABLE Lessons (id INT , mainMatUrl VARCHAR(100), extra_material_url VARCHAR (100), block_urls VARCHAR (1000), block_test_ids INT, main_test_id INT, next_lesson INT, PRIMARY KEY (id),FOREIGN KEY (main_test_id) REFERENCES Test (id),FOREIGN KEY (block_test_ids) REFERENCES Test(id));
ALTER TABLE Questions add FOREIGN KEY(correctAnswerId) REFERENCES Answers(id)
