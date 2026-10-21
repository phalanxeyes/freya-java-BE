/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2026-10-21 13:22:32.

export interface AlbumDTO {
    id: number;
    name: string;
    artist: string;
    coverUrl: string;
}

export interface CreateAlbumDTO {
    name: string;
    artist: string;
}

export interface UserPasswordValidationsDTO {
    required: boolean;
    min: number;
    max: number;
}

export interface UserUsernameValidationsDTO {
    required: boolean;
    min: number;
    max: number;
}

export interface UserValidationsDTO {
    password: UserPasswordValidationsDTO;
    username: UserUsernameValidationsDTO;
}

export interface CoverDTO {
    id: number;
    duration: number;
    playCount: number;
    song: SongDTO;
    createdAt: Date;
    updatedAt: Date;
}

export interface CreateCoverDTO {
    duration: number;
    songId: number;
    uploaderUserId: number;
    youtubeUrl: string;
}

export interface UpdateCoverDTO {
    youtubeUrl: string;
    duration: number;
    songId: number;
}

export interface HealthDTO {
    health: string;
}

export interface CreateSongDTO {
    duration: number;
    name: string;
    lyrics: string;
}

export interface SongDTO {
    id: number;
    name: string;
    duration: number;
    lyrics: string;
    album: AlbumDTO;
    createdAt: Date;
    updatedAt: Date;
}

export interface UpdateSongDTO {
    duration: number;
    name: string;
    lyrics: string;
    albumId: number;
}

export interface AuthUserDTO {
    user: UserDTO;
    accessToken: string;
}

export interface CreateUserDTO {
    username: string;
    email: string;
    password: string;
}

export interface UserDTO {
    id: number;
    username: string;
    createdAt: Date;
}

export interface UserSignInDTO {
    username: string;
    password: string;
}

export interface UserSignUpDTO {
    username: string;
    password: string;
}
