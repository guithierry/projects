interface BasicEntity {
    id: string;
    createdAt: Date;
    updatedAt: Date;
}

export type Status = "TODO" | "DOING" | "DONE";

export interface Project extends BasicEntity {
    status: Status;
    name: string;
    description: string;
    users: User[];
    owner: User;
}

export interface Todo extends BasicEntity {
    name: string;
    description: string;
    status: string;
    project: Project;
    assigned: User;
}

export interface User extends BasicEntity {
    name: string;
    email: string;
    password: string;
    token: string;
}

export interface Comment extends BasicEntity {
    description: string;
    user: User;
}
