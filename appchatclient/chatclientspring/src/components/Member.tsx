import React from "react";
import { User } from "../model/User";

interface MemberProps {
    user: User;
    memberOnClick: (user: User) => any;
}
export const MemberComponent: React.FC<MemberProps> = ({ user, memberOnClick }) => {
    return (
        <li className="p-2 border-bottom" onClick={() => memberOnClick(user)}>
            <a href="#!" className="d-flex justify-content-between">
                <div className="d-flex flex-row">
                    <div className="pt-1">
                        <p className="fw-bold mb-0">{user.fullName}</p>
                    </div>
                </div>
            </a>
        </li>
    )
}