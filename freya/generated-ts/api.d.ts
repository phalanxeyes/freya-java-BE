/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2026-08-26 21:00:52.

export interface AlbumDTO {
    id: number;
    name: string;
    artist: string;
}

export interface CreateAlbumDTO {
    name: string;
    artist: string;
}

export interface CoverDTO {
    id: number;
    duration: number;
    createdAt: Date;
    playCount: number;
    song: SongDTO;
}

export interface CreateCoverDTO {
    duration: number;
    songId: number;
    uploaderUserId: number;
    youtubeUrl: string;
}

export interface CreateSongDTO {
    duration: number;
    name: string;
    lyrics: string;
}

export interface SongDTO {
    id: number;
    duration: number;
    name: string;
    lyrics: string;
    album: AlbumDTO;
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
