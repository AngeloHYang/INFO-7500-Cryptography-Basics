from openai import OpenAI
import toml

def readKeyFromTomlFile(file="secrets.toml"):
    with open(file, "r") as f:
        data = toml.load(f)
    return data["OPENAI_API_KEY"]

client = OpenAI(api_key=readKeyFromTomlFile())

def textToSQL(schema, question):
    prompt = f"""
    You are an expert SQL developer. Given the following database schema and a question, write a SQL query to answer the question.
    
    Database schema:
    {schema}
    
    Question: {question}
    
    Respond with only the SQL query, nothing else.
    """
    

    
    completion = client.chat.completions.create(
        model="gpt-4o",
        messages=[
            {"role": "system", "content": "You are an expert SQL developer."},
            {"role": "user", "content": prompt}
        ]
    )
    return completion.choices[0].message.content

# Test
schema = """
CREATE TABLE students (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    enrollment_date DATE,
);
"""
question = "What is the incrementation of average age of students enrolled per year in the last 10 years?"
sql_query = textToSQL(schema, question)
print(sql_query)