# Some notes:
1. Application has 3 default users: user@abc.com (VIEW_INFO), admin@abc.com (VIEW_ADMIN) and super_admin@abc.com (VIEW_INFO, VIEW_ADMIN) with password="password";
2. "/info" page is available only for VIEW_INFO authority;
3. "/admin" page is available only for VIEW_ADMIN authority;
4. blocked users are displayed under "/admin" page.