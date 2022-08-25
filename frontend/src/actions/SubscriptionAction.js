export const changeSubscriptionStatus = (userEmail, programTitle, subscribeStatus) => {
    const params = queryString({userEmail: userEmail, programTitle: programTitle, subscribeStatus: subscribeStatus});
    return fetch('/travel-payouts/v1/subscription-status' + params, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then((response) => response);
}

export const blockUserProgram = (userEmail, programTitle) => {
    const params = queryString({userEmail: userEmail, programTitle: programTitle});
    return fetch('/travel-payouts/v1/subscription-status/block' + params, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then((response) => response);
}

export const queryString = (query = {}) => {
    const qs = Object.entries(query)
        .filter(pair => pair[1] !== undefined)
        .map(pair => pair.filter(i => i !== null).map(encodeURIComponent).join('='))
        .join('&');

    return qs && '?' + qs;
}
