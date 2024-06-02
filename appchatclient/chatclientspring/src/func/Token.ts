
export function getIdByToken(): number {
    const token = localStorage.getItem('token');
    if (token) {
        const payload = token.split('.')[1];
        const data = JSON.parse(atob(payload));
        return data.id;
    }
    return 0;
}
export function getUsernameByToken(): string {
    const token = localStorage.getItem('token');
    if (token) {
        const payload = token.split('.')[1];
        const data = JSON.parse(atob(payload));
        console.log("data.sub: ", data.sub);
        return data.sub;
    }
    return '';
}
export function getTokens(): string {
    const token = localStorage.getItem('token');
    if (token) {
        return token;
    }
    return '';
}

