/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2026-08-25 10:28:13.

export interface CoverDTO {
    id: number;
    duration: number;
    createdAt: Date;
    playCount: number;
    song: SongDTO;
}

export interface CreateCoverDTO {
    duration: number;
    createdAt: Date;
    playCount: number;
}

export interface CreateSongDTO {
    duration: number;
    name: string;
    lyrics: string;
}

export interface SongDTO {
    duration: number;
    name: string;
    lyrics: string;
}

export interface CreateUserDTO {
    userName: string;
    email: string;
    password: string;
}

export interface UserDTO {
    id: number;
    userName: string;
    createdAt: Date;
}
