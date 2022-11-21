function MyComponent() {

    const apiUrl = 'http://localhost:8081/task/api/v1/persons'

    fetch(apiUrl)
        .then((response) => response.join)
        .then((data) => console.log('This is my data', data))

    return (
        <div className='data'>
            <h1>Look is log</h1>
        </div>
    )
}

export default MyComponent