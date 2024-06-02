import { useEffect, useRef, useState } from "react";
import { getAllUser, getUserById } from "../api/UserApi";
import { User } from "../model/User";
import { MemberComponent } from "../components/Member";
import { over } from 'stompjs';
import SockJS from "sockjs-client";
import { getAllMessageBySenderIdAndReceiverId } from "../api/MessageApi";
import { Message } from "../model/Message";
import { getIdByToken, getTokens } from "../func/Token";
import ChatSection from "./Chat";
let isConnected = false;
var stompClient: any = null;
const MainPage: React.FC = () => {
    const [members, setMembers] = useState<User[]>([]);
    const [receiver, setReceiver] = useState<User>(
        {
            id: 0,
            fullName: "",
            email: "",
            password: "",
            username: ""
        }
    );
    const [currentMessage, setCurrentMessage] = useState<Message>(
        {
            id: 0,
            senderId: 0,
            receiverId: 0,
            message: "",
            senderName: "",
            receiverName: ""
        }
    );
    const messagesEndRef = useRef<HTMLDivElement>(null);
    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
    };

    const [currentUser, setCurrentUser] = useState({
        id: getIdByToken(),
        fullName: "",
        email: "",
        password: "",
        username: ""
    });
    const [messages, setMessages] = useState<Message[]>([]);
    const [tab, setTab] = useState(1);
    const memberComponentOnclick = (user: User) => {
        setTab(user.id);
    }
    useEffect(() => {
        scrollToBottom();
    }, [messages]);
    useEffect(() => {
        getAllMessageBySenderIdAndReceiverId(getIdByToken(), tab)
            .then(response => {
                console.log("response: ", response);
                setMessages(response)
            })
        getUserById(tab)
            .then(response => setReceiver(response))
    }, [tab]);
    useEffect(() => {
        getAllUser()
            .then(response => {
                setMembers(response)
                setTab(response[0].id)
            })
        connect();
    }, []);

    const connect = () => {
        if (isConnected && stompClient) {
            console.log("Socket is already connected.");
            return;
        }
        let Sock = new SockJS('http://localhost:8080/ws');
        stompClient = over(Sock);
        // Tạo một object chứa token trong header
        const headers = {
            Authorization: `Bearer ${getTokens()}` // Thay YOUR_TOKEN bằng token thực tế
        };
        stompClient.connect(headers, onConnected, onError);
        isConnected = true;
    }
    const onError = (err: string) => {
        console.log(err);
    }
    const onConnected = () => {
        getUserById(currentUser.id).then(response => setCurrentUser({ ...response }));
        stompClient.subscribe('/user/' + currentUser.id + '/private', onPrivateMessage);
    }
    const handleMessage = (event: any) => {
        const { value } = event.target;
        setCurrentMessage({ ...currentMessage, "message": value, "senderId": currentUser.id, "receiverId": tab, "senderName": currentUser.username, "receiverName": receiver.username });
    }

    const onPrivateMessage = (payload: any) => {
        const message = JSON.parse(payload.body);
        setMessages(prevMessages => [...prevMessages, message]);
        setTab(message.senderId);
    };
    const sendPrivateValue = () => {
        const chatMessage = {
            id: currentMessage.id + 2,
            receiverId: receiver.id,
            senderId: currentUser.id,
            message: currentMessage.message,
            senderName: currentUser.username,
            receiverName: receiver.username
        };
        const headers = {
            Authorization: `Bearer ${getTokens()}` // Thay YOUR_TOKEN bằng token thực tế
        };
        stompClient.send("/app/private-message", headers, JSON.stringify(chatMessage));
        console.log("messages: ", messages);

        setMessages(prevMessages => [...prevMessages, chatMessage]);
        setCurrentMessage({ ...currentMessage, message: "", senderId: currentUser.id, receiverId: tab, senderName: currentUser.username, receiverName: receiver.username });
    };
    return (
        <div style={{ background: '#ccc' }}>
            <div className="container py-5">
                <div className="row">
                    <div className="col-md-6 col-lg-5 col-xl-4 mb-4 mb-md-0">
                        <h5 className="font-weight-bold mb-3 text-center text-lg-start">Member</h5>
                        <div className="card">
                            <div className="card-body">
                                <ul className="list-unstyled mb-0">
                                    {members.map(user =>
                                        < MemberComponent key={user.id} user={user} memberOnClick={memberComponentOnclick} />
                                    )}
                                </ul>
                            </div>
                        </div>

                    </div>
                    <div className="col-md-6 col-lg-7 col-xl-8">
                        <ul className="list-unstyled">
                            {messages.map(message =>
                                <ChatSection key={message.id} name={message.senderName === currentUser.username ? "Bạn" : message.senderName} message={message.message} isSender={message.senderName === currentUser.username} />)
                            }
                            <div ref={messagesEndRef} />
                            <li className="bg-white mb-3">
                                <div data-mdb-input-init className="form-outline">
                                    <textarea onChange={handleMessage} value={currentMessage.message} className="form-control" id="textAreaExample2" ></textarea>
                                    <label className="form-label">Message</label>
                                </div>
                            </li>
                            <button onClick={sendPrivateValue} type="button" data-mdb-button-init data-mdb-ripple-init className="btn btn-info btn-rounded float-end">Send</button>
                        </ul>

                    </div>

                </div>

            </div>
        </div>
    );
}

export default MainPage;
