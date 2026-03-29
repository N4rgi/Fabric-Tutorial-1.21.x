#version 150

uniform sampler2D DiffuseSampler;
uniform float BlinkProgress;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 color = texture(DiffuseSampler, texCoord);

    // distanza verticale dal centro
    float dist = abs(texCoord.y - 0.5);

    // palpebre che si chiudono dal centro verso fuori
    if (dist > (0.5 - BlinkProgress * 0.5)) {
        fragColor = vec4(0.0, 0.0, 0.0, 1.0);
    } else {
        fragColor = color;
    }
}