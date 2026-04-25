# Release Checklist

## Version

- [ ] `VERSION` bumped exactly once for the release
- [ ] version follows SemVer (`MAJOR.MINOR.PATCH`)
- [ ] release notes match the changes in this version

## Library Build

- [ ] `:leaflekt:assembleRelease` passes
- [ ] `:leaflekt:testDebugUnitTest` passes
- [ ] `:leaflekt:publishReleasePublicationToMavenLocal` passes
- [ ] release AAR is generated at `leaflekt/build/outputs/aar/leaflekt-release.aar`
- [ ] sources jar is published to `mavenLocal`

## JitPack Readiness

- [ ] `jitpack.yml` pins a compatible JDK
- [ ] `leaflekt` uses `maven-publish`
- [ ] release publication is built from `components.release`
- [ ] publication has stable coordinates
- [ ] README includes JitPack install instructions
- [ ] README uses the published artifact coordinates

## Documentation

- [ ] public API examples match the current code
- [ ] release install snippet is correct
- [ ] feature list matches current SDK behavior
- [ ] security policy exists
- [ ] repository license is explicit and committed
- [ ] known limitations are documented

## Security

- [ ] no secrets committed to the repository
- [ ] workflow permissions are scoped to minimum required access
- [ ] release automation runs only on `master`
- [ ] branch protection is enabled for `master`
- [ ] GitHub Actions allowed actions policy is reviewed
- [ ] GitHub private vulnerability reporting is enabled

## GitHub Repository Settings

- [ ] default branch is `master`
- [ ] Actions are enabled
- [ ] workflow permission allows `contents: write`
- [ ] maintainer can create releases

## Release Automation

- [ ] push to `master` with a new `VERSION` creates tag `v<version>`
- [ ] the workflow creates a GitHub Release for the same tag
- [ ] the workflow uploads the release AAR asset
- [ ] the workflow warms the JitPack build URL
- [ ] repeated pushes without a new version do not create duplicate releases

## Post Release

- [ ] JitPack release page resolves successfully
- [ ] sample dependency install works in a fresh consumer project
- [ ] GitHub Release notes look correct
