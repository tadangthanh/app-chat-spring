import { getUsernameByToken } from "../func/Token";
import { User } from "../model/User";
export async function request(url: string) {
    const token = localStorage.getItem('token');
    const response = await fetch(url, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    if (!response.ok) throw new Error("Failed to fetch data");
    return await response.json();
}


export async function getAllUser(): Promise<User[]> {
    const url = "http://localhost:8080/api/users/ignore/" + getUsernameByToken();
    const users = await request(url);
    console.log(users);
    return users.map((user: User) => {
        return new User(user.id, user.fullName, user.email, user.password, user.username);
    });
}
export async function getUserById(id: number): Promise<User> {
    const url = `http://localhost:8080/api/users/${id}`;
    const user = await request(url);
    return new User(user.id, user.fullName, user.email, user.password, user.username);
}
export async function getUserByUsername(username: string): Promise<User> {
    const url = `http://localhost:8080/api/users/username/${username}`;
    const user = await request(url);
    return new User(user.id, user.fullName, user.email, user.password, user.username);
}