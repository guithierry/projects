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
}

export interface Todo extends BasicEntity {
    name: string;
    description: string;
    status: string;
    project: Project;
}
export interface User extends BasicEntity {
    name: string;
    email: string;
    password: string;
    picture: string;
}
export interface Comment extends BasicEntity {
    description: string;
}
