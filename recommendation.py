import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import sys

def load_data():
    enrollments = pd.read_csv('output/enrollments.csv', header=None, names=['email', 'course_id'])
    courses = pd.read_csv('output/courses.csv', header=None, names=['course_id', 'title', 'description', 'job_titles'])
    return enrollments, courses

def recommend_courses(email):
    enrollments, courses = load_data()
    user_courses = enrollments[enrollments['email'] == email]['course_id']

    if user_courses.empty:
        print("[]")
        return

    # Get job titles of enrolled courses
    user_job_titles = courses[courses['course_id'].isin(user_courses)]['job_titles'].tolist()
    user_job_titles = " ".join(user_job_titles)

    # Calculate similarity between user job titles and all course job titles
    tfidf_vectorizer = TfidfVectorizer(stop_words='english')
    tfidf_matrix = tfidf_vectorizer.fit_transform([user_job_titles] + courses['job_titles'].tolist())
    cosine_sim = cosine_similarity(tfidf_matrix[0:1], tfidf_matrix[1:]).flatten()

    # Get top 5 similar courses
    similar_indices = cosine_sim.argsort()[-5:][::-1]
    recommended_courses = courses.iloc[similar_indices]

    print(recommended_courses.to_json(orient='records'))

if __name__ == '__main__':
    action = sys.argv[1]

    if action == 'retrain':
        # For this example, there's no need to retrain the model as we use similarity measures directly.
        pass
    elif action == 'recommend':
        email = sys.argv[2]
        recommend_courses(email)
