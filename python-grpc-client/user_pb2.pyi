from google.protobuf.internal import enum_type_wrapper as _enum_type_wrapper
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from typing import ClassVar as _ClassVar, Optional as _Optional, Union as _Union

DESCRIPTOR: _descriptor.FileDescriptor

class Gender(int, metaclass=_enum_type_wrapper.EnumTypeWrapper):
    __slots__ = []
    MALE: _ClassVar[Gender]
    FEMALE: _ClassVar[Gender]
    OTHER: _ClassVar[Gender]
MALE: Gender
FEMALE: Gender
OTHER: Gender

class UserRequest(_message.Message):
    __slots__ = ["username"]
    USERNAME_FIELD_NUMBER: _ClassVar[int]
    username: str
    def __init__(self, username: _Optional[str] = ...) -> None: ...

class UserResponse(_message.Message):
    __slots__ = ["id", "username", "name", "gender", "age"]
    ID_FIELD_NUMBER: _ClassVar[int]
    USERNAME_FIELD_NUMBER: _ClassVar[int]
    NAME_FIELD_NUMBER: _ClassVar[int]
    GENDER_FIELD_NUMBER: _ClassVar[int]
    AGE_FIELD_NUMBER: _ClassVar[int]
    id: int
    username: str
    name: str
    gender: Gender
    age: int
    def __init__(self, id: _Optional[int] = ..., username: _Optional[str] = ..., name: _Optional[str] = ..., gender: _Optional[_Union[Gender, str]] = ..., age: _Optional[int] = ...) -> None: ...
