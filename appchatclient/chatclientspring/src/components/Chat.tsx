import React from 'react';
interface ChatSectionProps {
    name: string;
    message: string;

}
const ChatSection: React.FC<ChatSectionProps> = ({ name, message }) => {
    return (
        <li className="d-flex justify-content-between mb-4">
            <div className="card">
                <div className="card-header d-flex justify-content-between p-3">
                    <p className="fw-bold mb-0">{name}</p>
                </div>
                <div className="card-body">
                    <p className="mb-0">
                        {message}
                    </p>
                </div>
            </div>
        </li>
    );
}
export default ChatSection;
