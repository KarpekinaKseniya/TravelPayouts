export const findAllUsers = () => {
    return fetch('/travel-payouts/v1/users')
        .then(response => response.json());
}

export const findAllUserPrograms = (id) => {
    return fetch('/travel-payouts/v1/user/' + id + '/programs')
        .then(response => response.json());
}

export const createUser = (user) => {
    return fetch('/travel-payouts/v1/user', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user),
    });
}
